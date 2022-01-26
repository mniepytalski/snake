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
        this(point.getX(), point.getY());
    }

    public static Point get(int x, int y) {
        return new Point(x, y);
    }

    public Point add(Point a) {
        return new Point(x + a.getX(), y + a.getY());
    }

    public Point add(int value) {
        return new Point(x + value, y + value);
    }

    public Point minus(Point a) {
        return new Point(x - a.getX(), y - a.getY());
    }

    public Point minus(int value) {
        return new Point(x - value, y - value);
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

    public static Point Random(int x, int y) {
        return new Point((int) (Math.random() * x ), (int) (Math.random() * y ));
    }

    public String toString() {
        return "(x:"+x+",y:"+y+")";
    }
}
