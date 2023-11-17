package pl.cbr.games.snake;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.config.PositionConfig;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.geom2d.Square;
import pl.cbr.games.snake.levels.Level;
import pl.cbr.games.snake.objects.Apple;
import pl.cbr.games.snake.objects.Lemon;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.Wall;
import pl.cbr.games.snake.objects.player.Player;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;

@Data
@Component
public class GameModel {

    private GameConfig gameConfig;

    private GameStatus status = GameStatus.RUNNING;
    private final List<OnePointObject> objects = new ArrayList<>();
    private final List<Player> players= new ArrayList<>();

    private final Rectangle board;
    private OnePointObject collisionPoint = null;

    public GameModel(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        board = new Rectangle(Point2D.of(0, 0),
                (Point2D.of(gameConfig.getWidth(), gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }

    public void initLevelObjects(Level level) {
        getObjects().clear();
        IntStream.rangeClosed(1, level.getApples()).forEach(n -> getObjects().add(new Apple()));
        IntStream.rangeClosed(1, level.getWalls()).forEach(n -> getObjects().add(new Wall()));
        IntStream.rangeClosed(1, level.getLemons()).forEach(n -> getObjects().add(new Lemon()));
    }

    public List<Square> initPlayers() {
        List<Square> forbiddenAreas = getPlayers().stream()
                .map(Player::getPlayerModel)
                .map(PlayerModel::getPlayerConfig)
                .map(PlayerConfig::getPosition)
                .map(PositionConfig::getPoint)
                .map(point -> new Square(point,5)).toList();

        getObjects().forEach(o -> o.setRandomPosition(getBoard().getRightBottom()));
        return forbiddenAreas;
    }

    public void addPlayer(Player player) {
        getPlayers().add(player);
    }

    public void clearBots() {
        List<Player> livePlayers = getPlayers().stream().filter(player -> !player.isBot()).toList();
        getPlayers().clear();
        getPlayers().addAll(livePlayers);
    }

    public Map<Point2D, List<OnePointObject>> detectDuplicates() {
        return getObjects().stream()
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
