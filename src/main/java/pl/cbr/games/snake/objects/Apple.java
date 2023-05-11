package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Apple extends OnePointObject {

    public Apple(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public GameResource getImage() {
        return GameResource.APPLE;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {
        playerModel.addLength(5);
        super.actionOnPlayerHit(playerModel);
    }
}
