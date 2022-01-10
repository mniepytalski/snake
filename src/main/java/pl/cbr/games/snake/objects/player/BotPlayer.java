package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.objects.player.mind.MoveStrategy;

import java.awt.event.KeyEvent;

public class BotPlayer extends Player {

    private final MoveStrategy mind;

    public BotPlayer(BoardModel boardModel, PlayerConfig playerConfig, GameConfig gameConfig, ResourceLoader resourceLoader) {
        super(boardModel, playerConfig, gameConfig, resourceLoader);
        playerState = new PlayerState();
        mind = new MoveStrategy( this, boardModel);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void moveBot() {
        mind.calculateMove();
    }
}
