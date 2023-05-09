package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerAndControlsConfig;
import pl.cbr.games.snake.gfx.GameGraphics;

import java.awt.event.KeyEvent;

public class LivePlayer extends Player {

    public LivePlayer(BoardModel boardModel, PlayerAndControlsConfig playerConfig, GameConfig gameConfig, ResourceLoader resourceLoader, GameGraphics gfx) {
        super(boardModel, playerConfig, gameConfig, resourceLoader, gfx);
        playerState = new PlayerState(playerConfig.getControl());
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }
}
