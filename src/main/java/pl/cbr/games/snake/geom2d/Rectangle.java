package pl.cbr.games.snake.geom2d;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rectangle {
    private final Point2D leftTop;
    private final Point2D rightBottom;

    public boolean isOutside(Point2D point) {
        return point.getY() < leftTop.getY()
                || point.getY() >= rightBottom.getY()
                || point.getX() < leftTop.getX()
                || point.getX() >= rightBottom.getX();
    }

    public boolean isInside(Point2D point) {
        return !isOutside(point);
    }
}
