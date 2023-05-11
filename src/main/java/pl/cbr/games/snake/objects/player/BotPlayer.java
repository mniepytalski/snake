package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.BoardLogic;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.mind.MoveStrategy;

import java.awt.event.KeyEvent;

public class BotPlayer extends Player {

    private final MoveStrategy mind;

    public BotPlayer(BoardLogic boardLogic, PlayerConfig playerConfig, Collision collision) {
        super(boardLogic, playerConfig, collision);
        playerState = new PlayerState();
        mind = new MoveStrategy( this, boardLogic, collision);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void move() {
        mind.calculateMove();
        super.move();
    }
}
