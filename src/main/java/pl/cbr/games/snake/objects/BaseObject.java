package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    public final BoardModel boardModel;

    final UUID uuid;

    public BaseObject(BoardModel boardModel) {
        this.boardModel = boardModel;
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
