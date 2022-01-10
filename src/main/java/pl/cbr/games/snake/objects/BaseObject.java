package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.UUID;

@Data
public abstract class BaseObject {

    public final BoardModel boardModel;
    public final GameConfig gameConfig;
    public final GameResources gameResources;

    final UUID uuid;

    public BaseObject(BoardModel boardModel, GameConfig gameConfig, GameResources gameResources) {
        this.boardModel = boardModel;
        this.gameConfig = gameConfig;
        this.gameResources = gameResources;
        uuid = UUID.randomUUID();
    }

    public abstract boolean isEndGame();

    public abstract void actionOnPlayerHit(PlayerModel playerModel);
}
