package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.gfx.GameGraphics;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Lemon extends OnePointObject implements Drawing {

    public Lemon(BoardModel boardModel, GameGraphics gfx) {
        super(boardModel, gfx);
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
