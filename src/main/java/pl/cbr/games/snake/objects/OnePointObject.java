package pl.cbr.games.snake.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public abstract class OnePointObject extends BaseObject implements Drawing {

    private Point2D position;

    protected OnePointObject(GameConfig gameConfig, BoardModel boardModel, ResourceLoader resourceLoader) {
        super(boardModel, gameConfig, resourceLoader);
        this.position = new Point2D();
    }

    public void setRandomPosition() {
        setPosition(Point2D.Random(boardModel.getBoard().getRightBottom().getX(), boardModel.getBoard().getRightBottom().getY()));
    }

    public void setPosition(Point2D point) {
        position.set(point);
    }

    public Image getImage() {
        return null;
    }

    public void doDrawing(Graphics g) {
        Point2D position = getPosition().multiply(gameConfig.getDotSize());
        g.drawImage(getImage(), position.getX(), position.getY(), null);
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
