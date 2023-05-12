package pl.cbr.games.snake.objects.player.mind;

import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.BotPlayer;

@Slf4j
public class MoveStrategy extends MoveStrategyBase {

    public MoveStrategy(BotPlayer player, GameLogic gameLogic, Collision collision) {
        super(player, gameLogic, collision);
    }

    public void calculateMove(BotPlayer player) {
        log.debug("move:{}", player.getPlayerState().getDirection());
        if ( canIMove() ) {
            changeDirectionIfPossible();
        } else {
            avoidingObstacles();
        }
    }
}
