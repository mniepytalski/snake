package pl.cbr.games.snake.objects.player.mind;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.player.BotPlayer;

@Slf4j
@Service
public class MoveStrategy extends MoveStrategyBase {

    public MoveStrategy(Collision collision) {
        super(collision);
    }

    public void calculateMove(BotPlayer player) {
        log.debug("move:{}", player.getPlayerState().getDirection());
        if ( canIMove() ) {
            changeDirectionIfPossible(player);
        } else {
            avoidingObstacles(player);
        }
    }
}
