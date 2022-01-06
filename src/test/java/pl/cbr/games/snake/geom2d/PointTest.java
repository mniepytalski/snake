package pl.cbr.games.snake.geom2d;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointTest {
    @Test
    public void testAddition() {
        // given
        Point pointA = new Point(10,10);
        Point pointB = new Point(20,30);

        //
        Point pointC = pointA.add(pointB);

        //
        Assertions.assertEquals(30, pointC.getX());
        Assertions.assertEquals(40, pointC.getY());
    }

    @Test
    public void testMinus() {
        // given
        Point pointA = new Point(20,30);
        Point pointB = new Point(10,10);

        //
        Point pointC = pointA.minus(pointB);

        //
        Assertions.assertEquals(10, pointC.getX());
        Assertions.assertEquals(20, pointC.getY());
    }

    @Test
    public void testSet() {
        // given
        Point pointA = new Point(20,30);
        Point pointB = new Point(10,10);

        //
        pointA.set(pointB);

        //
        Assertions.assertEquals(10, pointA.getX());
        Assertions.assertEquals(10, pointA.getY());
    }

    @Test
    public void testConstructor() {
        // given
        Point pointA = new Point(20,30);

        //
        Point pointB = new Point(pointA);

        //
        Assertions.assertEquals(20, pointB.getX());
        Assertions.assertEquals(30, pointB.getY());
    }

    @Test
    public void testMultiply() {
        // given
        Point pointA = new Point(20,30);
        int value = 10;

        //
        Point pointB = pointA.multiply(value);

        //
        Assertions.assertEquals(200, pointB.getX());
        Assertions.assertEquals(300, pointB.getY());
    }

    @Test
    public void testDivision() {
        // given
        Point pointA = new Point(200,300);
        int value = 10;

        //
        Point pointB = pointA.division(value);

        //
        Assertions.assertEquals(20, pointB.getX());
        Assertions.assertEquals(30, pointB.getY());
    }
}
