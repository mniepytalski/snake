package pl.cbr.games.snake.objects.player;

import lombok.Getter;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point2D;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerModel {
    private int length;
    private final List<Point2D> view;
    private final GameConfig gameConfig;

    private final PlayerConfig playerConfig;
    private final Collision collision;

    private final DirectionService directionService;
    private int points;

    public PlayerModel(GameConfig gameConfig, PlayerConfig playerConfig, Collision collision) {
        view = new ArrayList<>();
        this.gameConfig = gameConfig;
        this.playerConfig = playerConfig;
        this.collision = collision;
        directionService = new DirectionService();
    }

    public void initPlayer() {
        Point2D startPosition = playerConfig.getPosition().getPoint();
        view.clear();
        this.length = gameConfig.getDotsOnStart();
        initPlayerView(startPosition);
    }

    private void initPlayerView(Point2D startPosition) {
        for (int z = 0; z < getLength(); z++) {
            view.add((new Point2D(startPosition.getX() - z, startPosition.getY())));
        }
        points = 0;
    }

    public String getName() {
        return getPlayerConfig().getName();
    }

    public void move(MoveDirection direction) {
        addHead(getHead().add(directionService.getVector(direction)));
        limitTail();
        limitTail();
    }

    private void limitTail() {
        if ( getLength() < view.size() ) {
            view.remove(view.size() - 1);
        }
    }

    public int getViewSize() {
        return view.size();
    }

    public boolean checkOurselfCollision() {
        return collision.check(view);
    }


    public void addLength(int value) {
        length += value;
        points += value;
    }

    public void setLength(int value) {
        this.length = value;
    }

    public Point2D get(int z) {
        return view.get(z);
    }

    public Point2D getHead() {
        return view.get(0);
    }

    private void addHead(Point2D point) {
        view.add(0,point);
    }
}
