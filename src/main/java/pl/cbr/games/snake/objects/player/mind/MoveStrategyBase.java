package pl.cbr.games.snake.objects.player.mind;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.geom2d.Point;
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
    BoardModel boardModel;

    int moveDelay = 5;
    int counter = 0;

    public MoveStrategyBase(BotPlayer player, BoardModel boardModel) {
        this.player = player;
        this.boardModel = boardModel;
    }

    boolean canIMove() {
        return counter++ > moveDelay + (Math.random()*100);
    }

    void youCanMove() {
        counter = 0;
    }

    void oppositeDirection() {
        switch(player.getPlayerState().getDirection()) {
            case RIGHT:
                setDirection(MoveDirection.LEFT);
                break;
            case DOWN:
                setDirection(MoveDirection.UP);
                break;
            case LEFT:
                setDirection(MoveDirection.RIGHT);
                break;
            case UP:
                setDirection(MoveDirection.DOWN);
                break;
        }
        youCanMove();
    }

    void turnLeft(MoveDirection startDirection) {
        this.startDirection = startDirection;
        switch(player.getPlayerState().getDirection()) {
            case RIGHT:
                setDirection(MoveDirection.UP);
                break;
            case DOWN:
                setDirection(MoveDirection.RIGHT);
                break;
            case LEFT:
                setDirection(MoveDirection.DOWN);
                break;
            case UP:
                setDirection(MoveDirection.LEFT);
                break;
        }
        youCanMove();
    }

    void turnRight() {
        switch(player.getPlayerState().getDirection()) {
            case RIGHT:
                setDirection(MoveDirection.DOWN);
                break;
            case DOWN:
                setDirection(MoveDirection.LEFT);
                break;
            case LEFT:
                setDirection(MoveDirection.UP);
                break;
            case UP:
                setDirection(MoveDirection.RIGHT);
                break;
        }
        youCanMove();
    }

    private void setDirection(MoveDirection direction) {
        player.getPlayerState().setDirection(direction);
    }

    private MoveDirection getDirection() {
        return player.getPlayerState().getDirection();
    }

    Point calcNextPosition() {
        return calcNextPosition(player.getPlayerModel().getHead());
    }

    Point calcNextPosition(Point nextPosition) {
        DirectionService directionService = new DirectionService();
        Point moveVector = directionService.getVector(player.getPlayerState().getDirection());
        return nextPosition.add(moveVector);
    }

    boolean avoidingObstacles() {
        startDirection = player.getPlayerState().getDirection();
        Point nextPosition = calcNextPosition();
        Optional<OnePointObject> optionalBoardObject = boardModel.checkCollisions(nextPosition, player.getId());
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), getDirection());
                turnLeft(getStartDirection());
                log.debug("step2:{}->{}",getStartDirection(), getDirection());
                boardModel.checkCollisions(calcNextPosition(), player.getId()).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                oppositeDirection();
                                log.debug("step3:{}->{}", getStartDirection(), getDirection());
                            }
                        }
                );
                return true;
            }
        }
        return false;
    }

    boolean changeDirectionIfPossible() {
        startDirection = player.getPlayerState().getDirection();
        if (Math.random() > 0.5) {
            turnLeft(startDirection);
        } else {
            turnRight();
        }
        Point nextPosition = calcNextPosition();
        Optional<OnePointObject> optionalBoardObject = boardModel.checkCollisions(nextPosition, player.getId());
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                log.debug("step1:{}->{}",getStartDirection(), getDirection());
                oppositeDirection();
                log.debug("step2:{}->{}",getStartDirection(), getDirection());
                boardModel.checkCollisions(calcNextPosition(), player.getId()).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() ) {
                                setDirection(startDirection);
                                log.debug("step3:{}->{}", getStartDirection(), getDirection());
                            }
                        }
                );
                return true;
            }
        }
        return false;
    }
}
