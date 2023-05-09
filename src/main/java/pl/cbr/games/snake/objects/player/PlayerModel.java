package pl.cbr.games.snake.objects.player;

import lombok.Getter;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerModel {
    private int length;
    private final List<Point> view;
    private final GameConfig gameConfig;

    private final PlayerConfig playerConfig;

    private final DirectionService directionService;
    private int points;

    public PlayerModel(GameConfig gameConfig, PlayerConfig playerConfig) {
        view = new ArrayList<>();
        this.gameConfig = gameConfig;
        this.playerConfig = playerConfig;
        directionService = new DirectionService();
    }

    public void initPlayer() {
        Point startPosition = playerConfig.getPosition().getPoint();
        view.clear();
        this.length = gameConfig.getDotsOnStart();
        initPlayerView(startPosition);
    }

    private void initPlayerView(Point startPosition) {
        for (int z = 0; z < getLength(); z++) {
            view.add((new Point(startPosition.getX() - z, startPosition.getY())));
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
        return Collision.check(view);
    }

    public boolean isOutside(Rectangle boardModel) {
        return boardModel.isOutside(getHead());
    }

    public void addLength(int value) {
        length += value;
        points += value;
    }

    public void setLength(int value) {
        this.length = value;
    }

    public Point get(int z) {
        return view.get(z);
    }

    public Point getHead() {
        return view.get(0);
    }

    private void addHead(Point point) {
        view.add(0,point);
    }
}
