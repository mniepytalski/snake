package pl.cbr.games.snake.geom2d;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point2D {
    private int x;
    private int y;

    public Point2D(Point2D point) {
        this(point.getX(), point.getY());
    }

    public static Point2D get(int x, int y) {
        return new Point2D(x, y);
    }

    public Point2D add(Point2D a) {
        return new Point2D(x + a.getX(), y + a.getY());
    }

    public Point2D add(int value) {
        return new Point2D(x + value, y + value);
    }

    public Point2D minus(Point2D a) {
        return new Point2D(x - a.getX(), y - a.getY());
    }

    public Point2D minus(int value) {
        return new Point2D(x - value, y - value);
    }

    public void set(Point2D a) {
        x = a.getX();
        y = a.getY();
    }

    public Point2D multiply(int value) {
        return new Point2D(getX() * value, getY() * value);
    }

    public Point2D division(int value) {
        return new Point2D(getX() / value, getY() / value);
    }

    public static Point2D Random(int x, int y) {
        return new Point2D((int) (Math.random() * x ), (int) (Math.random() * y ));
    }

    public String toString() {
        return "(x:"+x+",y:"+y+")";
    }
}
