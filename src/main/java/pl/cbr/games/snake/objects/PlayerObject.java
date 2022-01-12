package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;

public class PlayerObject extends OnePointObject {

    public PlayerObject(GameConfig gameConfig, ResourceLoader resourceLoader, BoardModel boardModel) {
        super(gameConfig, boardModel, resourceLoader);
    }
}
