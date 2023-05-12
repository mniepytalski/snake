package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.mind.MoveStrategy;

import java.awt.event.KeyEvent;

public class BotPlayer extends Player {

    private final MoveStrategy mind;

    public BotPlayer(GameLogic gameLogic, PlayerConfig playerConfig, Collision collision) {
        super(gameLogic.getGameConfig().getDotsOnStart(), playerConfig);
        playerState = new PlayerState();
        mind = new MoveStrategy( this, gameLogic, collision);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void move() {
        mind.calculateMove(this);
        super.move();
    }
}
