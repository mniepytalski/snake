package pl.cbr.games.snake.objects;

import lombok.Data;
import lombok.ToString;
import pl.cbr.games.snake.GameModel;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
@ToString
public abstract class BaseObject {

    final UUID uuid;

    public BaseObject() {
        uuid = UUID.randomUUID();
    }

    public boolean isEndGame() {
        return true;
    }

    public abstract void actionOnPlayerHit(PlayerModel playerModel, Collision collision, GameModel model);
}
