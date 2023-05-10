package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.gfx.GameGraphics;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    public final BoardModel boardModel;
    public final GameGraphics gfx;

    final UUID uuid;

    public BaseObject(BoardModel boardModel, GameGraphics gfx) {
        this.boardModel = boardModel;
        this.gfx = gfx;
        uuid = UUID.randomUUID();
    }

    public boolean isEndGame() {
        return true;
    }

    public abstract void actionOnPlayerHit(PlayerModel playerModel);

    public String toString() {
        return "uuid:"+uuid;
    }
}
