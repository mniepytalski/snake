package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.config.PlayerAndControlsConfig;

import java.awt.event.KeyEvent;

public class LivePlayer extends Player {

    public LivePlayer(int dotsOnStart, PlayerAndControlsConfig playerConfig) {
        super(dotsOnStart, playerConfig);
        playerState = new PlayerState(playerConfig.getControl());
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }
}
