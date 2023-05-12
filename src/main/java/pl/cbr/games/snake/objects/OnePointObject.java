package pl.cbr.games.snake.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.player.PlayerModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public abstract class OnePointObject extends BaseObject {

    private Point2D position;

    protected OnePointObject() {
        super();
        this.position = Point2D.of(0, 0);
    }

    public void setRandomPosition(Point2D maxValue) {
        setPosition(Point2D.Random(maxValue.getX(), maxValue.getY()));
    }

    public void setPosition(Point2D point) {
        position.set(point);
    }

    public GameResource getImage() {
        return null;
    }


    public void actionOnPlayerHit(PlayerModel playerModel, GameLogic gameLogic) {
        for ( int i=0; i<100; i++ ) {
            // TODO - read it from game model
            setRandomPosition(gameLogic.getCollision().getBoard().getRightBottom());
            if (!gameLogic.collisionWithPoint()) {
                break;
            }
        }
    }
}
