package pl.cbr.games.snake.geom2d;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {

    @Test
    public void isOutsideTestFalse() {
        // given
        Rectangle rectangle = new Rectangle(new Point(0,0), new Point(200,100));
        Point point = new Point(100,50);

        //
        boolean test = rectangle.isOutside(point);

        //
        Assert.assertFalse(test);
    }

    @Test
    public void isOutsideTestTrue() {
        // given
        Rectangle rectangle = new Rectangle(new Point(0,0), new Point(200,100));
        Point point = new Point(300,50);

        //
        boolean test = rectangle.isOutside(point);

        //
        Assert.assertTrue(test);
    }
}
