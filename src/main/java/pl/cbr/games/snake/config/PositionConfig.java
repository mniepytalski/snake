package pl.cbr.games.snake.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cbr.games.snake.geom2d.Point2D;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionConfig {
    private int x;
    private int y;

    public Point2D getPoint() {
        return Point2D.of(x,y);
    }
}
