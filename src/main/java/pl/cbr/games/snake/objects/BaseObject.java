package pl.cbr.games.snake.objects;

import lombok.Data;
import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    public final BoardModel boardModel;
    public final GameConfig gameConfig;
    public final ResourceLoader resourceLoader;

    final UUID uuid;

    public BaseObject(BoardModel boardModel, GameConfig gameConfig, ResourceLoader resourceLoader) {
        this.boardModel = boardModel;
        this.gameConfig = gameConfig;
        this.resourceLoader = resourceLoader;
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
