package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.GameModel;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    final UUID uuid;
    
    public BaseObject() {
        uuid = UUID.randomUUID();
    }

    public boolean isEndGame() {
        return true;
    }

    public abstract void actionOnPlayerHit(PlayerModel playerModel, Collision collision, GameModel model);

    public String toString() {
        return "uuid:"+uuid;
    }
}
