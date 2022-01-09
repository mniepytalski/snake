package pl.cbr.games.snake;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.config.PositionConfig;
import pl.cbr.games.snake.gfx.BoardGraphics;
import pl.cbr.games.snake.levels.LevelScenarios;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.objects.player.BotPlayer;
import pl.cbr.games.snake.objects.player.LivePlayer;
import pl.cbr.games.snake.objects.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sound.sampled.*;
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
    private final transient GameResources gameResources;
    private final transient BoardModel boardModel;
    private final transient LevelScenarios levelScenarios;

    private final List<BotPlayer> botPlayers;

    public Board(SystemTimer systemTimer, GameConfig gameConfig, GameResources gameResources, BoardGraphics boardGraphics, BoardModel boardModel, LevelScenarios levelScenarios) {
        this.systemTimer = systemTimer;
        this.gameConfig = gameConfig;
        this.boardGraphics = boardGraphics;
        this.boardModel = boardModel;
        this.levelScenarios = levelScenarios;
        this.gameResources = gameResources;
        botPlayers = new ArrayList<>();
        this.setSize(gameConfig.getWidth(), gameConfig.getHeight());



        this.gameConfig.getPlayers().forEach(playerConfig -> boardModel.addPlayer(new LivePlayer(playerConfig, gameConfig, gameResources)));
        // TODO
        // add it to level scenarios
        // add collision with other players
        // in case of death not finishing level
//        BotPlayer botPlayer = new BotPlayer(boardModel, new PlayerConfig("Bot1", new PositionConfig(2,2)),  gameConfig, gameResources);
//        boardModel.addPlayer(botPlayer);
//        botPlayers.add(botPlayer);
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
        calcBotMoves();
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
                            boardObject.action(player.getPlayerModel());
                            playSound();
                            if ( player.getPlayerModel().getPoints()>=levelScenarios.getLevel().getPointsToFinish() ) {
                                levelScenarios.setNextLevel();
                                gameStatus = GameStatus.NEXT_LEVEL;
                            }
                        }
                    }
            );
            Optional<BoardObject> collisionStatus = player.checkCollision();
            if ( collisionStatus.isPresent() ) {
                actionOnCollision(player, collisionStatus.get());
            }
            if ( GameStatus.RUNNING == gameStatus ) {
                player.move();
            }
        });
        repaint();
    }

    private void actionOnCollision(Player player, BoardObject collisionObject) {
        log.warn("collision: {} -> {}, {}, {}", player.getPlayerConfig().getName(), collisionObject.getClass().getSimpleName(),
                player.getPlayerModel().getHead(), player.getPlayerState().getDirection());
        if (player.isBot()) {
            gameStatus = GameStatus.PAUSED;
            systemTimer.stop();
            //player.initPlayer();
        } else {
            gameStatus = GameStatus.STOP;
            systemTimer.stop();
        }
    }

    private void playSound() {
        String soundName = "data/eating1.wav";
        try {
            Class<?> cls = Class.forName("pl.cbr.games.snake.Board");
            var cLoader = cls.getClassLoader();
            URL url = cLoader.getResource(soundName);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | ClassNotFoundException | IOException | LineUnavailableException e) {
            log.error("Problem with read audio file",e);
        }
    }

    private void calcBotMoves() {
//        boardModel.getPlayers().stream().filter(Player::isBot).forEach(BotPlayer::moveBot);
        botPlayers.stream().forEach(BotPlayer::moveBot);
    }
}