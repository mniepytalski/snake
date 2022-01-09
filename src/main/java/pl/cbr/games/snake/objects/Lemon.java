package pl.cbr.games.snake.objects;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

public class Lemon extends BoardObject implements Drawing {

    public Lemon(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, boardModel, gameResources.getLemon());
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public void action(PlayerModel playerModel) {
        playerModel.setLength(4);
        super.action(playerModel);
    }
}
