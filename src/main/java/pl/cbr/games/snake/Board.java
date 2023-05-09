package pl.cbr.games.snake;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.gfx.BoardGraphics;
import pl.cbr.games.snake.levels.LevelScenarios;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.LivePlayer;
import pl.cbr.games.snake.objects.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.*;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@Component
public class Board extends JPanel implements ActionListener, Drawing {

    private boolean debug = false;
    private GameStatus gameStatus = GameStatus.RUNNING;

    private final SystemTimer systemTimer;
    private final transient GameConfig gameConfig;
    private final transient BoardGraphics boardGraphics;
    private final transient ResourceLoader resourceLoader;
    private final transient BoardModel boardModel;
    private final transient LevelScenarios levelScenarios;

    public Board(SystemTimer systemTimer, GameConfig gameConfig, ResourceLoader resourceLoader, BoardGraphics boardGraphics, BoardModel boardModel, LevelScenarios levelScenarios) {
        this.systemTimer = systemTimer;
        this.gameConfig = gameConfig;
        this.boardGraphics = boardGraphics;
        this.boardModel = boardModel;
        this.levelScenarios = levelScenarios;
        this.resourceLoader = resourceLoader;
        this.setSize(gameConfig.getWidth(), gameConfig.getHeight());
        this.gameConfig.getPlayers().forEach(playerConfig -> boardModel.addPlayer(new LivePlayer(boardModel, playerConfig, gameConfig, resourceLoader)));
        init();
    }

    private void init() {
        addKeyListener(new BoardKeyAdapter(this));
        boardGraphics.init(this);
        systemTimer.init(this);
        systemTimer.start();
        gameStatus = GameStatus.START_LOGO;
        initGame();
    }

    public void initGame() {
        boardModel.init(levelScenarios.getLevel());
        boardModel.getPlayers().forEach(Player::init);
    }

    @Override
    public void paintComponent(Graphics g) {
        log.debug("paintComponent, gameStatus:{}, timer:{}", gameStatus, systemTimer.isRunning());
        switch (gameStatus) {
            case RUNNING, PAUSED, NEXT_LEVEL, START_LOGO, STOP -> {
                super.paintComponent(g);
                doDrawing(g);
            }
        }
    }

    public void doDrawing(Graphics g) {
        if (gameStatus != GameStatus.NEXT_LEVEL && gameStatus != GameStatus.START_LOGO &&
                boardModel.getPlayers().stream().noneMatch(player -> player.getPlayerState().isInGame())) {
            gameStatus = GameStatus.STOP;
        }
        boardGraphics.printBoard(gameStatus,g,this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boardModel.getPlayers().stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            boardModel.checkCollisions(player).ifPresent(boardObject -> {
                        if ( boardObject.isEndGame() ) {
                            actionOnCollision(player, boardObject);
                        } else {
                            boardObject.actionOnPlayerHit(player.getPlayerModel());
                            if ( player.getPlayerModel().getPoints()>=levelScenarios.getLevel().getPointsToFinish()
                                    && !player.isBot()) {
                                levelScenarios.setNextLevel();
                                gameStatus = GameStatus.NEXT_LEVEL;
                                resourceLoader.playSound("nextLevel");
                            } else {
                                resourceLoader.playSound("eating1");
                            }
                        }
                    }
            );
            player.checkCollision().ifPresent(boardObject -> actionOnCollision(player, boardObject));
            if ( GameStatus.RUNNING == gameStatus ) {
                player.move();
            }
        });
        repaint();
    }

    private void actionOnCollision(Player player, OnePointObject collisionObject) {
        if ( gameStatus == GameStatus.STOP )
            return;
        log.warn("collision: {} -> {}, {}, {}", player.getName(), collisionObject.getClass().getSimpleName(),
                player.getPlayerModel().getHead(), player.getPlayerState().getDirection());
        if (player.isBot()) {
            player.init();
        } else {
            getBoardModel().setCollisionPoint(Optional.of(collisionObject));
            gameStatus = GameStatus.STOP;
        }
    }
}