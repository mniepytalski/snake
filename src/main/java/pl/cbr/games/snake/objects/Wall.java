package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.BoardLogic;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Wall extends OnePointObject {

    public Wall(BoardLogic boardLogic) {
        super(boardLogic);
    }

    @Override
    public GameResource getImage() {
        return GameResource.WALL;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {

    }
}
