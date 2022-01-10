package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

@Data
public abstract class OnePointObject extends BaseObject implements Drawing {

    private Point position;

    protected OnePointObject(GameConfig gameConfig, BoardModel boardModel, GameResources gameResources) {
        super(boardModel, gameConfig, gameResources);
        this.position = new Point();
    }

    public void setRandomPosition() {
        setPosition(Point.Random(boardModel.getBoard().getRightBottom().getX(), boardModel.getBoard().getRightBottom().getY()));
    }

    public void setPosition(Point point) {
        position.set(point);
    }

    public abstract Image getImage();

    public void doDrawing(Graphics g) {
        Point position = getPosition().multiply(gameConfig.getDotSize());
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

    public String toString() {
        return position.toString();
    }
}
