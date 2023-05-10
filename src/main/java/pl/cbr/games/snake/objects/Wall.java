package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.gfx.GameGraphics;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Wall extends OnePointObject implements Drawing {

    public Wall(BoardModel boardModel, GameGraphics gfx) {
        super(boardModel, gfx);
    }

    @Override
    public GameResource getImage() {
        return GameResource.WALL;
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel) {

    }
}
