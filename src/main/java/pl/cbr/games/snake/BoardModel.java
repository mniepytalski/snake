package pl.cbr.games.snake;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.config.PositionConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.levels.Level;
import pl.cbr.games.snake.objects.*;
import pl.cbr.games.snake.objects.player.BotPlayer;
import pl.cbr.games.snake.objects.player.Player;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;

@Slf4j
@Data
@Component
public class BoardModel {

    private final GameConfig gameConfig;
    private final ResourceLoader resourceLoader;
    private final Rectangle board;

    private final List<OnePointObject> objects;
    private final List<Player> players;

    public BoardModel(GameConfig gameConfig, ResourceLoader resourceLoader) {
        this.gameConfig = gameConfig;
        this.resourceLoader = resourceLoader;

        objects = new ArrayList<>();
        players = new ArrayList<>();

        board = new Rectangle(new Point(),
                (new Point(gameConfig.getWidth(), gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }

    public void init(Level level) {
        objects.clear();
        IntStream.rangeClosed(1, level.getApples()).forEach(n -> getObjects().add(new Apple(gameConfig, resourceLoader, this)));
        IntStream.rangeClosed(1, level.getWalls()).forEach(n -> getObjects().add(new Wall(gameConfig, resourceLoader, this)));
        IntStream.rangeClosed(1, level.getLemons()).forEach(n -> getObjects().add(new Lemon(gameConfig, resourceLoader, this)));

        clearBots();
        for (int i = 0; i < level.getBots(); i++) {
            addPlayer(new BotPlayer(this, new PlayerConfig("Bot" + i, new PositionConfig(2 + i * 5, 2 + i * 5)), gameConfig, resourceLoader));
        }

        objects.forEach(OnePointObject::setRandomPosition);
        tryingToChangeDuplicatePosition();
    }

    private void clearBots() {
        List<Player> livePlayers = players.stream().filter(player -> !player.isBot()).collect(Collectors.toList());
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

    public Optional<? extends OnePointObject> checkCollisions(Point playerPosition, UUID objectId) {
        Optional<Player> realPlayer = getPlayers().stream().filter(player -> !player.getUuid().equals(objectId))
                .filter(player -> Collision.check(player.getPlayerModel().getView(), playerPosition))
                .findFirst();
        if (realPlayer.isPresent()) {
            return realPlayer;
        }
        if (board.isOutside(playerPosition)) {
            return Optional.of(new RectObject(gameConfig, resourceLoader, null));
        }
        return getObjects().stream().filter(wall -> playerPosition.equals(wall.getPosition())
        ).findFirst();
    }

    private void tryingToChangeDuplicatePosition() {
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
        if (duplicates > 0) {
            log.info("{} init objects:{}, duplicates:{}", attempt, objects.size(), duplicates);
        }
    }

    private int getDuplicatesAndChangePosition() {
        Map<Point, List<OnePointObject>> duplicates = detectDuplicates();
        duplicates.forEach((k,v) -> v.stream().skip(1).forEach(OnePointObject::setRandomPosition));
        return duplicates.size();
    }

    private Map<Point, List<OnePointObject>> detectDuplicates() {
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
