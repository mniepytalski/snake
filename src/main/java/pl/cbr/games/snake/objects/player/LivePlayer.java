package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.config.PlayerAndControlsConfig;


public class LivePlayer extends Player {

    public LivePlayer(int dotsOnStart, PlayerAndControlsConfig playerConfig) {
        super(dotsOnStart, playerConfig);
        playerState = new PlayerState(playerConfig.getControl());
    }
}
