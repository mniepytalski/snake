package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardLogic;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    public final BoardLogic boardLogic;

    final UUID uuid;

    public BaseObject(BoardLogic boardLogic) {
        this.boardLogic = boardLogic;
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
