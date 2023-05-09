package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.gfx.GameGraphics;

public class PlayerObject extends OnePointObject {

    public PlayerObject(GameConfig gameConfig, BoardModel boardModel, GameGraphics gfx) {
        super(gameConfig, boardModel, gfx);
    }
}
