package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.gfx.BoardGraphics;
import pl.cbr.games.snake.gfx.GameGraphics;
import pl.cbr.games.snake.levels.LevelScenarios;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.LivePlayer;
import pl.cbr.games.snake.objects.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.swing.*;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@AllArgsConstructor
@Data
@Component
public class Board extends JPanel implements ActionListener, Drawing {

    private final GameModel gameModel;
    private final SystemTimer systemTimer;
    private final transient GameConfig gameConfig;
    private final transient BoardGraphics boardGraphics;
    private final transient ResourceLoader resourceLoader;
    private final GameGraphics gfx;
    private final transient BoardModel boardModel;
    private final transient LevelScenarios levelScenarios;

    @PostConstruct
    private void init() {
        gameModel.setStatus(GameStatus.RUNNING);
        this.setSize(gameConfig.getWidth(), gameConfig.getHeight());
        this.gameConfig.getPlayers().forEach(playerConfig -> boardModel.addPlayer(new LivePlayer(boardModel, playerConfig, gameConfig, resourceLoader, gfx)));
        addKeyListener(new BoardKeyAdapter(this));
        boardGraphics.init(this);
        systemTimer.init(this);
        systemTimer.start();
        gameModel.setStatus(GameStatus.START_LOGO);
        initGame();
    }

    public void initGame() {
        boardModel.init(levelScenarios.getLevel());
        boardModel.getPlayers().forEach(Player::init);
    }

    @Override
    public void paintComponent(Graphics g) {
        log.debug("paintComponent, gameStatus:{}, timer:{}", gameModel.getStatus(), systemTimer.isRunning());
        switch (gameModel.getStatus()) {
            case RUNNING, PAUSED, NEXT_LEVEL, START_LOGO, STOP -> {
                super.paintComponent(g);
                doDrawing(g);
            }
        }
    }

    public void doDrawing(Graphics g) {
        if (gameModel.getStatus() != GameStatus.NEXT_LEVEL && gameModel.getStatus() != GameStatus.START_LOGO &&
                boardModel.getPlayers().stream().noneMatch(player -> player.getPlayerState().isInGame())) {
            gameModel.setStatus(GameStatus.STOP);
        }
        boardGraphics.printBoard(gameModel.getStatus(), g, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boardModel.getPlayers().stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            boardModel.checkCollisions(player).ifPresent(boardObject -> {
                        if (boardObject.isEndGame()) {
                            actionOnCollision(player, boardObject);
                        } else {
                            boardObject.actionOnPlayerHit(player.getPlayerModel());
                            if (player.getPlayerModel().getPoints() >= levelScenarios.getLevel().getPointsToFinish()
                                    && !player.isBot()) {
                                levelScenarios.setNextLevel();
                                gameModel.setStatus(GameStatus.NEXT_LEVEL);
                                resourceLoader.playSound("nextLevel");
                            } else {
                                resourceLoader.playSound("eating1");
                            }
                        }
                    }
            );
            player.checkCollision().ifPresent(boardObject -> actionOnCollision(player, boardObject));
            if (GameStatus.RUNNING == gameModel.getStatus()) {
                player.move();
            }
        });
        repaint();
    }

    private void actionOnCollision(Player player, OnePointObject collisionObject) {
        if (gameModel.getStatus() == GameStatus.STOP)
            return;
        log.warn("collision: {} -> {}, {}, {}", player.getName(), collisionObject.getClass().getSimpleName(),
                player.getPlayerModel().getHead(), player.getPlayerState().getDirection());
        if (player.isBot()) {
            player.init();
        } else {
            getBoardModel().setCollisionPoint(Optional.of(collisionObject));
            gameModel.setStatus(GameStatus.STOP);
        }
    }
}