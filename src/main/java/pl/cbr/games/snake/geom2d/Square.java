package pl.cbr.games.snake.geom2d;

public class Square extends Rectangle {

    public Square(Point middle, int lengthOfEge) {
        super(middle.minus(lengthOfEge/2), middle.add(lengthOfEge/2));
    }
}
