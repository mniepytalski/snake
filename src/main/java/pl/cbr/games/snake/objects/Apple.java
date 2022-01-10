package pl.cbr.games.snake.objects;

import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

@Slf4j
public class Apple extends OnePointObject implements Drawing {

    public Apple(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, boardModel, gameResources);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public Image getImage() {
        return gameResources.getApple();
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {
        playerModel.addLength(5);
        super.actionOnPlayerHit(playerModel);
    }

    public String toString() {
        return "apple"+super.toString();
    }
}
