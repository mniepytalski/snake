package pl.cbr.games.snake.geom2d;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class SquareTest {

    @Test
    public void countPointsInsideSquare() {
        //
        Square square = Square.get(Point.get(10,10),3);
        List<Point> points = List.of(Point.get(1,1), Point.get(10,10), Point.get(9,9));

        //
        long result = points.stream().filter(square::isInside).count();

        //
        Assertions.assertEquals(result, 2);
    }

    @Test
    public void countPointsInsideSquares() {
        //
        List<Square> squares = List.of(
                Square.get(Point.get(10,10),3),
                Square.get(Point.get(0,0),4));
        List<Point> points = List.of(
                Point.get(1,1),
                Point.get(10,10),
                Point.get(9,9));

        //
        List<Point> results = points.stream().filter(point -> squares.stream().anyMatch(square -> square.isInside(point))).collect(Collectors.toList());

        //
        Assertions.assertEquals(results.size(), 3);
    }
}
