package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Wall extends OnePointObject {

    public Wall(GameLogic gameLogic) {
        super(gameLogic);
    }

    @Override
    public GameResource getImage() {
        return GameResource.WALL;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {

    }
}
