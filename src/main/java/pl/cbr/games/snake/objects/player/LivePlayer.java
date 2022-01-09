package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerAndControlsConfig;

import java.awt.event.KeyEvent;

public class LivePlayer extends Player {

    public LivePlayer(PlayerAndControlsConfig playerConfig, GameConfig gameConfig, GameResources gameResources) {
        super(playerConfig, gameConfig, gameResources);
        playerState = new PlayerState(playerConfig.getControl());
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }
}
