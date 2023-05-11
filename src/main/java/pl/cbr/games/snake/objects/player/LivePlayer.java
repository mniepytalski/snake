package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.BoardLogic;
import pl.cbr.games.snake.config.PlayerAndControlsConfig;
import pl.cbr.games.snake.geom2d.Collision;

import java.awt.event.KeyEvent;

public class LivePlayer extends Player {

    public LivePlayer(BoardLogic boardLogic, PlayerAndControlsConfig playerConfig, Collision collision) {
        super(boardLogic, playerConfig, collision);
        playerState = new PlayerState(playerConfig.getControl());
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }
}
