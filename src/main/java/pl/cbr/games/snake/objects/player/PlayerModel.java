package pl.cbr.games.snake.objects.player;

import lombok.Getter;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Point2D;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerModel {
    private int length;
    private final List<Point2D> view;
    private final int dotsOnStart;
    private final PlayerConfig playerConfig;
    private final DirectionService directionService;
    private int points;

    public PlayerModel(int dotsOnStart, PlayerConfig playerConfig) {
        view = new ArrayList<>();
        this.dotsOnStart = dotsOnStart;
        this.playerConfig = playerConfig;
        directionService = new DirectionService();
    }

    public void initPlayer() {
        Point2D startPosition = playerConfig.getPosition().getPoint();
        view.clear();
        this.length = dotsOnStart;
        initPlayerView(startPosition);
    }

    private void initPlayerView(Point2D startPosition) {
        for (int z = 0; z < getLength(); z++) {
            view.add((Point2D.of(startPosition.getX() - z, startPosition.getY())));
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
