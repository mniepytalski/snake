package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.config.PlayerAndControlsConfig;

import java.awt.event.KeyEvent;

public class LivePlayer extends Player {

    public LivePlayer(BoardModel boardModel, PlayerAndControlsConfig playerConfig) {
        super(boardModel, playerConfig);
        playerState = new PlayerState(playerConfig.getControl());
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }
}
