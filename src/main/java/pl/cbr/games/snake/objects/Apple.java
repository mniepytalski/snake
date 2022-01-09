package pl.cbr.games.snake.objects;

import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

@Slf4j
public class Apple extends BoardObject implements Drawing {

    public Apple(GameConfig gameConfig, GameResources gameResources, BoardModel boardModel) {
        super(gameConfig, boardModel, gameResources.getApple());
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public void action(PlayerModel playerModel) {
        playerModel.addLength(5);
        super.action(playerModel);
    }
}
