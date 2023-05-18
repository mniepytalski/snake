package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Apple extends OnePointObject {

    @Override
    public boolean isEndGame() {
        return false;
    }

    @Override
    public GameResource getImage() {
        return GameResource.APPLE;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel, Collision collision) {
        playerModel.addLength(5);
        super.actionOnPlayerHit(playerModel, collision);
    }
}
