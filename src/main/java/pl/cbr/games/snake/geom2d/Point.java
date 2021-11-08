package pl.cbr.games.snake.geom2d;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    private int x;
    private int y;

    public Point(Point point) {
        x = point.getX();
        y = point.getY();
    }

    public Point add(Point a) {
        return new Point(x + a.getX(), y + a.getY());
    }

    public Point minus(Point a) {
        return new Point(x - a.getX(), y - a.getY());
    }

    public void set(Point a) {
        x = a.getX();
        y = a.getY();
    }

    public Point multiply(int value) {
        return new Point(getX() * value, getY() * value);
    }

    public Point division(int value) {
        return new Point(getX() / value, getY() / value);
    }
}
