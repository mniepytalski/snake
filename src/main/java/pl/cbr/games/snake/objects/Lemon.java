package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Lemon extends OnePointObject {

    public Lemon(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public GameResource getImage() {
        return GameResource.LEMON;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {
        playerModel.setLength(4);
        super.actionOnPlayerHit(playerModel);
    }
}
