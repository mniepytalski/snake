package pl.cbr.games.snake.geom2d;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    public void testAddition() {
        // given
        Point2D pointA = new Point2D(10,10);
        Point2D pointB = new Point2D(20,30);

        //
        Point2D pointC = pointA.add(pointB);

        //
        Assertions.assertEquals(30, pointC.getX());
        Assertions.assertEquals(40, pointC.getY());
    }

    @Test
    public void testMinus() {
        // given
        Point2D pointA = new Point2D(20,30);
        Point2D pointB = new Point2D(10,10);

        //
        Point2D pointC = pointA.minus(pointB);

        //
        Assertions.assertEquals(10, pointC.getX());
        Assertions.assertEquals(20, pointC.getY());
    }

    @Test
    public void testSet() {
        // given
        Point2D pointA = new Point2D(20,30);
        Point2D pointB = new Point2D(10,10);

        //
        pointA.set(pointB);

        //
        Assertions.assertEquals(10, pointA.getX());
        Assertions.assertEquals(10, pointA.getY());
    }

    @Test
    public void testConstructor() {
        // given
        Point2D pointA = new Point2D(20,30);

        //
        Point2D pointB = new Point2D(pointA);

        //
        Assertions.assertEquals(20, pointB.getX());
        Assertions.assertEquals(30, pointB.getY());
    }

    @Test
    public void testMultiply() {
        // given
        Point2D pointA = new Point2D(20,30);
        int value = 10;

        //
        Point2D pointB = pointA.multiply(value);

        //
        Assertions.assertEquals(200, pointB.getX());
        Assertions.assertEquals(300, pointB.getY());
    }

    @Test
    public void testDivision() {
        // given
        Point2D pointA = new Point2D(200,300);
        int value = 10;

        //
        Point2D pointB = pointA.division(value);

        //
        Assertions.assertEquals(20, pointB.getX());
        Assertions.assertEquals(30, pointB.getY());
    }
}
