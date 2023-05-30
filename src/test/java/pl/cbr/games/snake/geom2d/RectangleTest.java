package pl.cbr.games.snake.geom2d;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {

    @Test
    public void isOutsideTestFalse() {
        // given
        Rectangle rectangle = new Rectangle(new Point2D(0,0), new Point2D(200,100));
        Point2D point = new Point2D(100,50);

        //
        boolean test = rectangle.isOutside(point);

        //
        Assert.assertFalse(test);
    }

    @Test
    public void isOutsideTestTrue() {
        // given
        Rectangle rectangle = new Rectangle(new Point2D(0,0), new Point2D(200,100));
        Point2D point = new Point2D(300,50);

        //
        boolean test = rectangle.isOutside(point);

        //
        Assert.assertTrue(test);
    }
}
