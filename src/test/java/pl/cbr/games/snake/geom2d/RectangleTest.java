package pl.cbr.games.snake.geom2d;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {

    @Test
    public void isOutsideTestFalse() {
        // given
        Rectangle rectangle = new Rectangle(Point2D.of(0,0), Point2D.of(200,100));
        Point2D point = Point2D.of(100,50);

        //
        boolean test = rectangle.isOutside(point);

        //
        Assert.assertFalse(test);
    }

    @Test
    public void isOutsideTestTrue() {
        // given
        Rectangle rectangle = new Rectangle(Point2D.of(0,0), Point2D.of(200,100));
        Point2D point = Point2D.of(300,50);

        //
        boolean test = rectangle.isOutside(point);

        //
        Assert.assertTrue(test);
    }
}
