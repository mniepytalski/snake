package pl.cbr.games.snake.objects.player.mind;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.BotPlayer;
import pl.cbr.games.snake.objects.player.DirectionService;
import pl.cbr.games.snake.objects.player.MoveDirection;
import pl.cbr.games.snake.objects.player.PlayerState;

import java.util.Optional;

@Slf4j
@Getter
public class MoveStrategyBase {

    MoveDirection startDirection;

    BotPlayer player;
    GameLogic gameLogic;
    Collision collision;

    int moveDelay = 5;
    int counter = 0;

    public MoveStrategyBase(BotPlayer player, GameLogic gameLogic, Collision collision) {
        this.player = player;
        this.gameLogic = gameLogic;
        this.collision = collision;
    }

    boolean canIMove() {
        return counter++ > moveDelay + (Math.random()*100);
    }

    private void youCanMove() {
        counter = 0;
    }

    private MoveDirection getDirection() {
        return player.getPlayerState().getDirection();
    }

    Point2D calcNextPosition() {
        return calcNextPosition(player.getPlayerModel().getHead());
    }

    Point2D calcNextPosition(Point2D nextPosition) {
        DirectionService directionService = new DirectionService();
        Point2D moveVector = directionService.getVector(player.getPlayerState().getDirection());
        return nextPosition.add(moveVector);
    }

    void avoidingObstacles() {
        PlayerState state = player.getPlayerState();
        startDirection = state.getDirection();
        Point2D nextPosition = calcNextPosition();
        Optional<? extends OnePointObject> optionalBoardObject = collision.check(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), getDirection());
                state.turnLeft(getStartDirection());
                youCanMove();
                log.debug("step2:{}->{}",getStartDirection(), getDirection());
                collision.check(calcNextPosition()).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                state.oppositeDirection();
                                youCanMove();
                                log.debug("step3:{}->{}", getStartDirection(), getDirection());
                            }
                        }
                );
            }
        }
    }

    void changeDirectionIfPossible() {
        PlayerState state = player.getPlayerState();
        startDirection = player.getPlayerState().getDirection();
        if (Math.random() > 0.5) {
            state.turnLeft(startDirection);
        } else {
            state.turnRight();
        }
        youCanMove();
        Point2D nextPosition = calcNextPosition();
        Optional<? extends OnePointObject> optionalBoardObject = collision.check(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), getDirection());
                state.oppositeDirection();
                youCanMove();
                log.debug("step2:{}->{}",getStartDirection(), getDirection());
                collision.check(calcNextPosition()).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                player.getPlayerState().setDirection(startDirection);
                                log.debug("step3:{}->{}", getStartDirection(), getDirection());
                            }
                        }
                );
            }
        }
    }
}
