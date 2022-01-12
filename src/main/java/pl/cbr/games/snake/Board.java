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
        initBoard();
    }

    private void initBoard() {
        boardGraphics.init(this);
        systemTimer.init(this);
        systemTimer.start();
        gameStatus = GameStatus.START_LOGO;
        initGame();
    }

    public void initGame() {
        initLevel();
        boardModel.getPlayers().forEach(Player::initPlayer);
        systemTimer.start();
    }

    private void initLevel() {
        boardModel.init(levelScenarios.getLevel());
    }

    @Override
    public void paintComponent(Graphics g) {
        log.debug("paintComponent, gameStatus:{}, timer:{}", gameStatus, systemTimer.isRunning());
        switch (gameStatus) {
            case RUNNING:
            case PAUSED:
            case NEXT_LEVEL:
            case START_LOGO:
            case STOP:
                super.paintComponent(g);
                doDrawing(g);
                break;
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
                            if ( player.getPlayerModel().getPoints()>=levelScenarios.getLevel().getPointsToFinish() ) {
                                levelScenarios.setNextLevel();
                                gameStatus = GameStatus.NEXT_LEVEL;
                                resourceLoader.playSound("nextLevel");
                            } else {
                                resourceLoader.playSound("eating1");
                            }
                        }
                    }
            );
            Optional<OnePointObject> collisionStatus = player.checkCollision();
            collisionStatus.ifPresent(boardObject -> actionOnCollision(player, boardObject));
            if ( GameStatus.RUNNING == gameStatus ) {
                player.move();
            }
        });
        repaint();
    }

    private void actionOnCollision(Player player, OnePointObject collisionObject) {
        log.warn("collision: {} -> {}, {}, {}", player.getPlayerConfig().getName(), collisionObject.getClass().getSimpleName(),
                player.getPlayerModel().getHead(), player.getPlayerState().getDirection());
        if (player.isBot()) {
            player.initPlayer();
        } else {
            gameStatus = GameStatus.STOP;
            systemTimer.stop();
        }
    }
}