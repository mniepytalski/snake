package pl.cbr.games.snake.geom2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Collision {

    public static boolean check(List<Point2D> points) {
        Set<Point2D> allItems = new HashSet<>();
        Set<Point2D> duplicates = points.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet());
        return !duplicates.isEmpty();
    }

    public static boolean check(List<Point2D> points, Point2D point) {
        List<Point2D> pointsToCheck = new ArrayList<>(points);
        pointsToCheck.add(point);
        Set<Point2D> allItems = new HashSet<>();
        return !pointsToCheck.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet()).isEmpty();
    }

    public static boolean check(List<Point2D> points1, List<Point2D> points2) {
        Set<Point2D> allItems = new HashSet<>(points1);
        return !points2.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet()).isEmpty();
    }
}
