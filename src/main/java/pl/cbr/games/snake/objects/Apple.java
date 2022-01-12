package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

@ToString(callSuper = true)
public class Apple extends OnePointObject implements Drawing {

    public Apple(GameConfig gameConfig, ResourceLoader resourceLoader, BoardModel boardModel) {
        super(gameConfig, boardModel, resourceLoader);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public Image getImage() {
        return resourceLoader.get(GameResource.APPLE);
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {
        playerModel.addLength(5);
        super.actionOnPlayerHit(playerModel);
    }
}
