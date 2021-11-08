package pl.cbr.games.snake.geom2d;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rectangle {
    private final Point leftTop;
    private final Point rightBottom;

    public boolean isOutside(Point point) {
        return point.getY() < leftTop.getY()
                || point.getY() >= rightBottom.getY()
                || point.getX() < leftTop.getX()
                || point.getX() >= rightBottom.getX();
    }
}
