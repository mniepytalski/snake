package pl.cbr.games.snake.objects;

import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.objects.player.PlayerModel;

@ToString(callSuper = true)
public class Lemon extends OnePointObject {

    public Lemon(BoardModel boardModel) {
        super(boardModel);
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
