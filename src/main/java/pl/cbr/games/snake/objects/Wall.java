package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

public class Wall extends BoardObject implements Drawing {

    public Wall(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, boardModel, gameResources.getWall());
    }

    @Override
    public boolean isEndGame() {
        return true;
    }

    @Override
    public void action(PlayerModel playerModel) {

    }
}
