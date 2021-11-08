package pl.cbr.games.snake.player.mind;

import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.player.BotPlayer;

public class AppleEaterStrategy extends MoveStrategyBase {

    public AppleEaterStrategy(BotPlayer player, BoardModel boardModel) {
        super(player, boardModel);
    }
}
