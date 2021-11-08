package pl.cbr.games.snake.objects;

import lombok.Data;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.player.PlayerModel;

import java.awt.*;

@Data
public abstract class BoardObject implements Drawing {

    private Point position;
    final BoardModel boardModel;
    final GameConfig gameConfig;
    final Image objectImage;

    protected BoardObject(GameConfig gameConfig, BoardModel boardModel, Image objectImage) {
        this.boardModel = boardModel;
        this.gameConfig = gameConfig;
        this.objectImage = objectImage;
        this.position = new Point();
    }

    public void setRandomPosition() {
        position.setX((int) (Math.random() * boardModel.getBoard().getRightBottom().getX()) );
        position.setY((int) (Math.random() * boardModel.getBoard().getRightBottom().getY()) );
    }

    public void doDrawing(Graphics g) {
        Point position = getPosition().multiply(gameConfig.getDotSize());
        g.drawImage(objectImage, position.getX(), position.getY(), null);
    }

    public abstract boolean isEndGame();

    public void action(PlayerModel playerModel) {
        for ( int i=0; i<100; i++ ) {
            setRandomPosition();
            if (!boardModel.collisionWithPoint()) {
                break;
            }
        }
    }
}
