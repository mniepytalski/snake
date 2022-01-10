package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

public class Wall extends OnePointObject implements Drawing {

    public Wall(GameConfig gameConfig, ResourceLoader resourceLoader, BoardModel boardModel) {
        super(gameConfig, boardModel, resourceLoader);
    }

    @Override
    public boolean isEndGame() {
        return true;
    }

    @Override
    public Image getImage() {
        return resourceLoader.get(GameResource.WALL);
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {

    }

    public String toString() {
        return "wall"+super.toString();
    }
}
