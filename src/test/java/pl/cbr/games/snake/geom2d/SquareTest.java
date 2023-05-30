package pl.cbr.games.snake.geom2d;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SquareTest {

    @Test
    public void countPointsInsideSquare() {
        //
        Square square = Square.get(Point2D.of(10,10),3);
        List<Point2D> points = List.of(Point2D.of(1,1), Point2D.of(10,10), Point2D.of(9,9));

        //
        long result = points.stream().filter(square::isInside).count();

        //
        Assertions.assertEquals(result, 2);
    }

    @Test
    public void countPointsInsideSquares() {
        //
        List<Square> squares = List.of(
                Square.get(Point2D.of(10,10),3),
                Square.get(Point2D.of(0,0),4));
        List<Point2D> points = List.of(
                Point2D.of(1,1),
                Point2D.of(10,10),
                Point2D.of(9,9));

        //
        List<Point2D> results = points.stream().filter(point -> squares.stream().anyMatch(square -> square.isInside(point))).toList();

        //
        Assertions.assertEquals(results.size(), 3);
    }
}
