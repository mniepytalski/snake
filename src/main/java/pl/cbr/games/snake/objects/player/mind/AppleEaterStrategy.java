package pl.cbr.games.snake.objects.player.mind;

import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.BotPlayer;

public class AppleEaterStrategy extends MoveStrategyBase {

    public AppleEaterStrategy(BotPlayer player, Collision collision) {
        super(player, collision);
    }
}
