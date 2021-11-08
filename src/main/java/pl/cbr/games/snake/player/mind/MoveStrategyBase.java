package pl.cbr.games.snake.player.mind;

import pl.cbr.games.snake.Board;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.objects.BoardObject;
import pl.cbr.games.snake.player.BotPlayer;
import pl.cbr.games.snake.player.DirectionService;
import pl.cbr.games.snake.player.MoveDirection;

import java.util.Optional;

public class MoveStrategyBase {

    BotPlayer player;
    BoardModel boardModel;

    int moveDelay = 5;
    int counter = 0;

    public MoveStrategyBase(BotPlayer player, BoardModel boardModel) {
        this.player = player;
        this.boardModel = boardModel;
    }

    boolean canMove() {
        return counter++ > moveDelay;
    }

    void youCanMove() {
        counter = 0;
    }

    void opositeDirection() {
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

    void turnLeft() {
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

    Point calcNextPosition() {
        return calcNextPosition(player.getPlayerModel().getHead());
    }

    Point calcNextPosition(Point nextPosition) {
        DirectionService directionService = new DirectionService();
        Point moveVector = directionService.getVector(player.getPlayerState().getDirection());
        return nextPosition.add(moveVector);
    }

    boolean avoidingObstacles() {
        Point nextPosition = calcNextPosition();
        Optional<BoardObject> optionalBoardObject = boardModel.checkCollisions(nextPosition);
        if ( optionalBoardObject.isPresent()) {
            if ( optionalBoardObject.get().isEndGame() ) {
                turnLeft();
                boardModel.checkCollisions(calcNextPosition(nextPosition)).ifPresent(
                        boardObject -> {
                            if ( boardObject.isEndGame() )
                                opositeDirection();
                        }
                );
                return true;
            }
        } else {
            if (boardModel.isOutsideBoard(nextPosition) ) {
                turnRight();
                return true;
            }
        }
        return false;
    }
}
