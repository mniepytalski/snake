package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.gfx.BoardGraphics;
import pl.cbr.games.snake.levels.LevelScenarios;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.LivePlayer;
import pl.cbr.games.snake.objects.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.annotation.PostConstruct;
import javax.swing.*;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@AllArgsConstructor
@Data
@Component
public class Game extends JPanel implements ActionListener, Drawing {

    private final Collision collision;
    private final GameModel model;
    private final SystemTimer systemTimer;
    private final transient GameConfig gameConfig;
    private final transient BoardGraphics boardGraphics;
    private final transient ResourceLoader resourceLoader;
    private final transient GameLogic gameLogic;
    private final transient LevelScenarios levelScenarios;

    @PostConstruct
    private void init() {
        model.setStatus(GameStatus.RUNNING);
        setSize(gameConfig.getWidth(), gameConfig.getHeight());
        gameConfig.getPlayers().forEach(playerConfig -> model.addPlayer(new LivePlayer(gameLogic, playerConfig)));
        addKeyListener(new GameKeyAdapter(this, model));
        setBackground(Color.black);
        setFocusable(true);
        Dimension dimension = new Dimension(gameConfig.getWidth(), gameConfig.getHeight());
        setPreferredSize(dimension);
        systemTimer.initAndStart(this);
        model.setStatus(GameStatus.START_LOGO);
        initGame();
    }

    public void initGame() {
        gameLogic.initLevel(levelScenarios.getLevel());
        model.getPlayers().forEach(Player::init);
    }

    @Override
    public void paintComponent(Graphics g) {
        log.debug("paintComponent, gameStatus:{}, timer:{}", model.getStatus(), systemTimer.isRunning());
        switch (model.getStatus()) {
            case RUNNING, PAUSED, NEXT_LEVEL, START_LOGO, STOP -> {
                super.paintComponent(g);
                doDrawing(g);
            }
        }
    }

    public void doDrawing(Graphics g) {
        if (model.getStatus() != GameStatus.NEXT_LEVEL && model.getStatus() != GameStatus.START_LOGO &&
                model.getPlayers().stream().noneMatch(player -> player.getPlayerState().isInGame())) {
            model.setStatus(GameStatus.STOP);
        }
        boardGraphics.printBoard(model.getStatus(), g, this);
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.getPlayers().stream().filter(player -> player.getPlayerState().isInGame()).forEach(player -> {
            collision.check(player).ifPresent(boardObject -> {
                        if (boardObject.isEndGame()) {
                            actionOnCollision(player, boardObject);
                        } else {
                            boardObject.actionOnPlayerHit(player.getPlayerModel(), gameLogic);
                            if (player.getPlayerModel().getPoints() >= levelScenarios.getLevel().getPointsToFinish()
                                    && !player.isBot()) {
                                levelScenarios.setNextLevel();
                                model.setStatus(GameStatus.NEXT_LEVEL);
                                resourceLoader.playSound("nextLevel");
                            } else {
                                resourceLoader.playSound("eating1");
                            }
                        }
                    }
            );
            collision.checkCollision(player).ifPresent(boardObject -> actionOnCollision(player, boardObject));
            if (GameStatus.RUNNING == model.getStatus()) {
                player.move();
            }
        });
        repaint();
    }

    private void actionOnCollision(Player player, OnePointObject collisionObject) {
        player.getPlayerState().setInGame(false);
        if (model.getStatus() == GameStatus.STOP)
            return;
        log.warn("collision: {} -> {}, {}, {}", player.getName(), collisionObject.getClass().getSimpleName(),
                player.getPlayerModel().getHead(), player.getPlayerState().getDirection());
        if (player.isBot()) {
            player.init();
        } else {
            model.setCollisionPoint(collisionObject);
            model.setStatus(GameStatus.STOP);
        }
    }
}