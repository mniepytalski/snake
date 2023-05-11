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

    void oppositeDirection() {
        switch(player.getPlayerState().getDirection()) {
            case RIGHT -> setDirection(MoveDirection.LEFT);
            case DOWN -> setDirection(MoveDirection.UP);
            case LEFT -> setDirection(MoveDirection.RIGHT);
            case UP -> setDirection(MoveDirection.DOWN);
        }
        youCanMove();
    }

    void turnLeft(MoveDirection startDirection) {
        this.startDirection = startDirection;
        switch(player.getPlayerState().getDirection()) {
            case RIGHT -> setDirection(MoveDirection.UP);
            case DOWN -> setDirection(MoveDirection.RIGHT);
            case LEFT -> setDirection(MoveDirection.DOWN);
            case UP -> setDirection(MoveDirection.LEFT);
        }
        youCanMove();
    }

    void turnRight() {
        switch(player.getPlayerState().getDirection()) {
            case RIGHT -> setDirection(MoveDirection.DOWN);
            case DOWN -> setDirection(MoveDirection.LEFT);
            case LEFT -> setDirection(MoveDirection.UP);
            case UP -> setDirection(MoveDirection.RIGHT);
        }
        youCanMove();
    }

    private void youCanMove() {
        counter = 0;
    }

    private void setDirection(MoveDirection direction) {
        player.getPlayerState().setDirection(direction);
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
        startDirection = player.getPlayerState().getDirection();
        Point2D nextPosition = calcNextPosition();
        Optional<? extends OnePointObject> optionalBoardObject = collision.check(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), getDirection());
                turnLeft(getStartDirection());
                log.debug("step2:{}->{}",getStartDirection(), getDirection());
                collision.check(calcNextPosition()).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                oppositeDirection();
                                log.debug("step3:{}->{}", getStartDirection(), getDirection());
                            }
                        }
                );
            }
        }
    }

    void changeDirectionIfPossible() {
        startDirection = player.getPlayerState().getDirection();
        if (Math.random() > 0.5) {
            turnLeft(startDirection);
        } else {
            turnRight();
        }
        Point2D nextPosition = calcNextPosition();
        Optional<? extends OnePointObject> optionalBoardObject = collision.check(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), getDirection());
                oppositeDirection();
                log.debug("step2:{}->{}",getStartDirection(), getDirection());
                collision.check(calcNextPosition()).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                setDirection(startDirection);
                                log.debug("step3:{}->{}", getStartDirection(), getDirection());
                            }
                        }
                );
            }
        }
    }
}
