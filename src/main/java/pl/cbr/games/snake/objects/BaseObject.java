package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    public final GameLogic gameLogic;

    final UUID uuid;

    public BaseObject(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
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
