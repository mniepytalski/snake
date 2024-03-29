package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.objects.player.mind.MoveStrategy;

import java.awt.event.KeyEvent;

public class BotPlayer extends Player {

    private final MoveStrategy mind;

    public BotPlayer(int dotsOnStart, PlayerConfig playerConfig, MoveStrategy mind) {
        super(dotsOnStart, playerConfig);
        playerState = new PlayerState();
        this.mind = mind;
    }

    public void keyPressed(KeyEvent e) {
    }

    public void move() {
        mind.calculateMove(this);
        super.move();
    }
}
