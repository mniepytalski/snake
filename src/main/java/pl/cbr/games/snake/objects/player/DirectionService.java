package pl.cbr.games.snake.objects.player;

import pl.cbr.games.snake.geom2d.Point;

import java.util.EnumMap;
import java.util.Map;

public class DirectionService {
    private final Map<MoveDirection, Point> directions;

    public DirectionService() {
        directions = new EnumMap<>(MoveDirection.class);
        prepareDirectionMap();
    }

    private void prepareDirectionMap() {
        directions.clear();
        directions.put(MoveDirection.LEFT, new Point(-1,0));
        directions.put(MoveDirection.RIGHT, new Point(1,0));
        directions.put(MoveDirection.UP, new Point(0,-1));
        directions.put(MoveDirection.DOWN, new Point(0,1));
    }

    public Point getVector(MoveDirection direction) {
        if ( directions.containsKey(direction) ) {
            return directions.get(direction);
        } else {
            return new Point();
        }
    }
}
