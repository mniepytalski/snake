package pl.cbr.games.snake;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.config.PositionConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.geom2d.Square;
import pl.cbr.games.snake.gfx.GameGraphics;
import pl.cbr.games.snake.levels.Level;
import pl.cbr.games.snake.objects.*;
import pl.cbr.games.snake.objects.player.BotPlayer;
import pl.cbr.games.snake.objects.player.Player;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;

@Slf4j
@Data
@Component
public class BoardModel {

    private final GameConfig gameConfig;
    private final GameGraphics gfx;
    private final Rectangle board;

    private final List<OnePointObject> objects = new ArrayList<>();
    private final List<Player> players= new ArrayList<>();

    private Optional<OnePointObject> collisionPoint;

    public BoardModel(GameConfig gameConfig, GameGraphics gfx) {
        this.gameConfig = gameConfig;
        this.gfx = gfx;
        board = new Rectangle(new Point2D(),
                (new Point2D(gameConfig.getWidth(), gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }

    public void init(Level level) {
        objects.clear();
        IntStream.rangeClosed(1, level.getApples()).forEach(n -> getObjects().add(new Apple( this)));
        IntStream.rangeClosed(1, level.getWalls()).forEach(n -> getObjects().add(new Wall(this)));
        IntStream.rangeClosed(1, level.getLemons()).forEach(n -> getObjects().add(new Lemon(this)));

        clearBots();
        for (int i = 0; i < level.getBots(); i++) {
            addPlayer(new BotPlayer(this, new PlayerConfig("Bot" + i, new PositionConfig(2 + i * 5, 2 + i * 5))));
        }
        List<Square> forbiddenAreas = getPlayers().stream()
                .map(Player::getPlayerModel)
                .map(PlayerModel::getPlayerConfig)
                .map(PlayerConfig::getPosition)
                .map(PositionConfig::getPoint)
                .map(point -> new Square(point,5)).collect(Collectors.toList());

        objects.forEach(OnePointObject::setRandomPosition);
        tryingToChangeDuplicatePosition(forbiddenAreas);
        collisionPoint = Optional.empty();
    }

    private void clearBots() {
        List<Player> livePlayers = players.stream().filter(player -> !player.isBot()).toList();
        players.clear();
        players.addAll(livePlayers);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean collisionWithPoint() {
        return !detectDuplicates().isEmpty();
    }

    public Optional<? extends OnePointObject> checkCollisions(Player player) {
        Optional<Player> realPlayer = getPlayers().stream()
                .filter(p -> !p.getUuid().equals(player.getUuid()))
                .filter(p -> Collision.check(p.getPlayerModel().getView(), player.getPlayerModel().getHead()))
                .findFirst();
        if (realPlayer.isPresent()) {
            return realPlayer;
        }
        return getObjects().stream().filter(wall -> player.getPlayerModel().getHead().equals(wall.getPosition())
        ).findFirst();
    }

    public Optional<? extends OnePointObject> checkCollisions(Point2D playerPosition) {
        Optional<Player> realPlayer = getPlayers().stream()
                .filter(player -> Collision.check(player.getPlayerModel().getView(), playerPosition))
                .findFirst();
        if (realPlayer.isPresent()) {
            return realPlayer;
        }
        if (board.isOutside(playerPosition)) {
            return Optional.of(new RectObject(null));
        }
        return getObjects().stream().filter(wall -> playerPosition.equals(wall.getPosition())).findFirst();
    }

    private void tryingToChangeDuplicatePosition(List<Square> forbiddenAreas) {
        int attempt = 1;
        int duplicates = getDuplicatesAndChangePosition();
        for (int x = 0; x < 200; x++) {
            if (duplicates > 0) {
                getDuplicatesAndChangePosition();
                ++attempt;
            } else {
                break;
            }
        }
        int forbiddenAreasCount = changePositionFromForbiddenAreas(forbiddenAreas);
        if (duplicates > 0) {
            log.info("{} init objects:{}, duplicates:{}, forbidden:{}", attempt, objects.size(), duplicates, forbiddenAreasCount);
        }
    }

    private int getDuplicatesAndChangePosition() {
        Map<Point2D, List<OnePointObject>> duplicates = detectDuplicates();
        duplicates.forEach((k,v) -> v.stream().skip(1).forEach(OnePointObject::setRandomPosition));
        return duplicates.size();
    }

    private int changePositionFromForbiddenAreas(List<Square> forbiddenAreas) {
        List<OnePointObject> pointsToChange = objects.stream().filter(p ->
                forbiddenAreas.stream().anyMatch(area -> area.isInside(p.getPosition()))).toList();
        pointsToChange.forEach(OnePointObject::setRandomPosition);
        return pointsToChange.size();
    }

    private Map<Point2D, List<OnePointObject>> detectDuplicates() {
        return objects.stream()
                .collect(
                        collectingAndThen(
                                Collectors.groupingBy(OnePointObject::getPosition)
                                , m -> {
                                    m.values().removeIf(v -> v.size() <= 1L);
                                    return m;
                                })
                );
    }
}
