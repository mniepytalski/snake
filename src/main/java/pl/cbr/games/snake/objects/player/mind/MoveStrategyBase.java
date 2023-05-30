package pl.cbr.games.snake.objects.player.mind;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.*;

import java.util.Optional;

@Slf4j
@Getter
public class MoveStrategyBase {

    MoveDirection startDirection;

    Collision collision;

    int moveDelay = 5;
    int counter = 0;

    public MoveStrategyBase(Collision collision) {
        this.collision = collision;
    }

    boolean canIMove() {
        return counter++ > moveDelay + (Math.random()*100);
    }

    private void youCanMove() {
        counter = 0;
    }

    Point2D calcNextPosition(Player player) {
        Point2D nextPosition = player.getPlayerModel().getHead();
        DirectionService directionService = new DirectionService();
        Point2D moveVector = directionService.getVector(player.getPlayerState().getDirection());
        return nextPosition.add(moveVector);
    }

    void avoidingObstacles(Player player) {
        PlayerState state = player.getPlayerState();
        startDirection = state.getDirection();
        Point2D nextPosition = calcNextPosition(player);
        Optional<? extends OnePointObject> optionalBoardObject = collision.check(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), state.getDirection());
                state.turnLeft(getStartDirection());
                youCanMove();
                log.debug("step2:{}->{}",getStartDirection(), state.getDirection());
                collision.check(calcNextPosition(player)).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                state.oppositeDirection();
                                youCanMove();
                                log.debug("step3:{}->{}", getStartDirection(), state.getDirection());
                            }
                        }
                );
            }
        }
    }

    void changeDirectionIfPossible(Player player) {
        PlayerState state = player.getPlayerState();
        startDirection = player.getPlayerState().getDirection();
        if (Math.random() > 0.5) {
            state.turnLeft(startDirection);
        } else {
            state.turnRight();
        }
        youCanMove();
        Point2D nextPosition = calcNextPosition(player);
        Optional<? extends OnePointObject> optionalBoardObject = collision.check(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), state.getDirection());
                state.oppositeDirection();
                youCanMove();
                log.debug("step2:{}->{}",getStartDirection(), state.getDirection());
                collision.check(calcNextPosition(player)).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                player.getPlayerState().setDirection(startDirection);
                                log.debug("step3:{}->{}", getStartDirection(), state.getDirection());
                            }
                        }
                );
            }
        }
    }
}
