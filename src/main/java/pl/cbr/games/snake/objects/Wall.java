package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Wall extends OnePointObject {

    public Wall(BoardModel boardModel) {
        super(boardModel);
    }

    @Override
    public GameResource getImage() {
        return GameResource.WALL;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {

    }
}
