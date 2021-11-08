package pl.cbr.games.snake.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.cbr.games.snake.geom2d.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionConfig {
    private int x;
    private int y;

    public Point getPoint() {
        return new Point(x,y);
    }
}
