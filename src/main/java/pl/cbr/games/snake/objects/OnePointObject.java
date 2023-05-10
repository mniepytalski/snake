package pl.cbr.games.snake.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.player.PlayerModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public abstract class OnePointObject extends BaseObject {

    private Point2D position;

    protected OnePointObject(BoardModel boardModel) {
        super(boardModel);
        this.position = new Point2D();
    }

    public void setRandomPosition() {
        setPosition(Point2D.Random(boardModel.getBoard().getRightBottom().getX(), boardModel.getBoard().getRightBottom().getY()));
    }

    public void setPosition(Point2D point) {
        position.set(point);
    }

    public GameResource getImage() {
        return null;
    }

    public void actionOnPlayerHit(PlayerModel playerModel) {
        for ( int i=0; i<100; i++ ) {
            setRandomPosition();
            if (!boardModel.collisionWithPoint()) {
                break;
            }
        }
    }
}
