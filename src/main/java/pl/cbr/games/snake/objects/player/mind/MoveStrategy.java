package pl.cbr.games.snake.objects.player.mind;

import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.objects.player.BotPlayer;

@Slf4j
public class MoveStrategy extends MoveStrategyBase {

    public MoveStrategy(BotPlayer player, BoardModel boardModel) {
        super(player, boardModel);
    }

    public void calculateMove() {
        log.info("move:{}", player.getPlayerState().getDirection());
        if ( canIMove() ) {
            changeDirectionIfPossible();
        } else {
            if (avoidingObstacles()) {
                return;
            }
        }
    }
}
