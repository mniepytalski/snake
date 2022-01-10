package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

public class Lemon extends OnePointObject implements Drawing {

    public Lemon(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, boardModel, gameResources);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public Image getImage() {
        return gameResources.getLemon();
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {
        playerModel.setLength(4);
        super.actionOnPlayerHit(playerModel);
    }

    public String toString() {
        return "lemon"+super.toString();
    }
}
