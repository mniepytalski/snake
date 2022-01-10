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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;

@Slf4j
@Data
@Component
public class BoardModel {

    private final GameConfig gameConfig;
    private final GameResources gameResources;
    private final Rectangle board;

    private final List<OnePointObject> objects;
    private final List<Player> players;

    public BoardModel(GameConfig gameConfig, GameResources gameResources) {
        this.gameConfig = gameConfig;
        this.gameResources = gameResources;

        objects = new ArrayList<>();
        players = new ArrayList<>();

        board = new Rectangle(new Point(),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
    }

    public void init(Level level) {
        objects.clear();
        IntStream.rangeClosed(1, level.getApples()).forEach(n -> getObjects().add(new Apple(gameConfig, gameResources,this)));
        IntStream.rangeClosed(1, level.getWalls()).forEach(n -> getObjects().add(new Wall(gameConfig, gameResources, this)));
        IntStream.rangeClosed(1, level.getLemons()).forEach(n -> getObjects().add(new Lemon(gameConfig, gameResources, this)));

        clearBots();
        for ( int i=0; i< level.getBots(); i++) {
            addPlayer(new BotPlayer(this, new PlayerConfig("Bot"+i, new PositionConfig(2+i*5, 2+i*5)), gameConfig, gameResources));
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

//        Map<Point, List<OnePointObject>> dup1 = objects.stream()
//                .collect(
//                    collectingAndThen(
//                        Collectors.groupingBy(OnePointObject::getPosition)
//                    ,m -> { m.values().removeIf(v -> v.size() <= 1L); return m; })
//                );

        List<Point> positions = objects.stream().map(OnePointObject::getPosition).collect(Collectors.toList());
        players.forEach(player -> positions.addAll(player.getPlayerModel().getView()));
        Set<Point> duplicates = positions.stream().filter(i -> Collections.frequency(positions, i) > 1)
                .collect(Collectors.toSet());
        return !duplicates.isEmpty();
    }

    public Optional<OnePointObject> checkCollisions(Player player) {
        Optional<Player> realPlayer = getPlayers().stream()
                .filter(p -> p.getId()!=player.getId())
                .filter(p -> Collision.check(p.getPlayerModel().getView(),player.getPlayerModel().getHead()))
                .findFirst();
        if ( realPlayer.isPresent()) {
            return Optional.of(realPlayer.get());
        }
        return getObjects().stream().filter(wall -> player.getPlayerModel().getHead().equals(wall.getPosition())
        ).findFirst();
    }

    public Optional<OnePointObject> checkCollisions(Point playerPosition, int objectId) {
        Optional<Player> realPlayer = getPlayers().stream().filter(player -> player.getId()!=objectId)
                .filter(player -> Collision.check(player.getPlayerModel().getView(),playerPosition))
                .findFirst();
        if ( realPlayer.isPresent() ) {
            return Optional.of(realPlayer.get());
        }
        if (board.isOutside(playerPosition)) {
            return Optional.of(new RectObject(gameConfig, gameResources, null));
        }
        return getObjects().stream().filter(wall -> playerPosition.equals(wall.getPosition())
        ).findFirst();
    }

    private void tryingToChangeDuplicatePosition() {
        int attempt = 1;
        int duplicates = getDuplicatesAndChangePosition();
        for ( int x=0; x<200; x++ ) {
            if (duplicates > 0) {
                getDuplicatesAndChangePosition();
                ++attempt;
            } else {
                break;
            }
        }
        if ( duplicates>0 ) {
            log.info("{} init objects:{}, duplicates:{}", attempt, objects.size(), duplicates);
        }
    }

    private int getDuplicatesAndChangePosition() {
        List<List<OnePointObject>> duplicates = detectDuplicates();
        duplicates.forEach((dup) -> dup.forEach(OnePointObject::setRandomPosition));
        return duplicates.size();
    }

    private List<List<OnePointObject>> detectDuplicates() {
        Map<Point, List<OnePointObject>> maps = objects.stream().collect(Collectors.groupingBy(OnePointObject::getPosition));
        return maps.values().stream().filter(list -> list.size()>1).collect(Collectors.toList());
    }
}
