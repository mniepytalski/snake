package pl.cbr.games.snake.geom2d;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.GameModel;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.PlayerObject;
import pl.cbr.games.snake.objects.RectObject;
import pl.cbr.games.snake.objects.player.Player;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@Component
public class Collision {

    private final GameConfig gameConfig;
    private final GameModel model;

    private final Rectangle board;

    public Collision(GameConfig gameConfig, GameModel model) {
        this.gameConfig = gameConfig;
        this.model = model;
        board = model.getBoard();
    }
    public boolean isOutside(Point2D position) {
        return board.isOutside(position);
    }

    public boolean check(List<Point2D> points) {
        Set<Point2D> allItems = new HashSet<>();
        Set<Point2D> duplicates = points.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet());
        return !duplicates.isEmpty();
    }

    public boolean check(List<Point2D> points, Point2D point) {
        List<Point2D> pointsToCheck = new ArrayList<>(points);
        pointsToCheck.add(point);
        Set<Point2D> allItems = new HashSet<>();
        return !pointsToCheck.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet()).isEmpty();
    }

    public boolean check(List<Point2D> points1, List<Point2D> points2) {
        Set<Point2D> allItems = new HashSet<>(points1);
        return !points2.stream().filter(n -> !allItems.add(n)).collect(Collectors.toSet()).isEmpty();
    }

    public Optional<? extends OnePointObject> check(Player player) {
        Optional<Player> realPlayer = model.getPlayers().stream()
                .filter(p -> !p.getUuid().equals(player.getUuid()))
                .filter(p -> check(p.getPlayerModel().getView(), player.getPlayerModel().getHead()))
                .findFirst();
        if (realPlayer.isPresent()) {
            return realPlayer;
        }
        return model.getObjects().stream().filter(wall -> player.getPlayerModel().getHead().equals(wall.getPosition())
        ).findFirst();
    }

    public Optional<? extends OnePointObject> check(Point2D playerPosition) {
        Optional<Player> realPlayer = model.getPlayers().stream()
                .filter(player -> check(player.getPlayerModel().getView(), playerPosition))
                .findFirst();
        if (realPlayer.isPresent()) {
            return realPlayer;
        }
        if (board.isOutside(playerPosition)) {
            return Optional.of(new RectObject());
        }
        return model.getObjects().stream().filter(wall -> playerPosition.equals(wall.getPosition())).findFirst();
    }

    public boolean check(PlayerModel playerModel) {
        return check(playerModel.getView());
    }

    public Optional<OnePointObject> checkCollision(Player player) {
        if ( check(player.getPlayerModel()) ) {
            return Optional.of(new PlayerObject());
        }
        if (isOutside(player.getPlayerModel().getHead())) {
            return Optional.of(new RectObject());
        }
        return Optional.empty();
    }
}
