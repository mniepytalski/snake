package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;

import java.awt.*;

public class RectObject extends OnePointObject {

    public RectObject(GameConfig gameConfig, ResourceLoader resourceLoader, BoardModel boardModel) {
        super(gameConfig, boardModel, resourceLoader);
    }

    @Override
    public boolean isEndGame() {
        return true;
    }

    @Override
    public Image getImage() {
        return null;
    }
}
