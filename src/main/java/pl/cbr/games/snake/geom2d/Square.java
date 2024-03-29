package pl.cbr.games.snake.geom2d;

public class Square extends Rectangle {

    public Square(Point2D middle, int lengthOfEge) {
        super(middle.minus(lengthOfEge/2), middle.add(lengthOfEge/2));
    }

    public static Square get(Point2D middle, int lengthOfEge) {
        return new Square(middle, lengthOfEge);
    }
}
