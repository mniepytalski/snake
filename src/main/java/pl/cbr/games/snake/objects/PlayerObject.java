package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;

public class PlayerObject extends BoardObject {

    public PlayerObject(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, boardModel, gameResources.getApple());
    }

    @Override
    public boolean isEndGame() {
        return true;
    }
}
