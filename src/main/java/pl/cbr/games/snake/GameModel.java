package pl.cbr.games.snake;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.Player;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

@Data
@Component
public class GameModel {

    private GameStatus status;
    private final List<OnePointObject> objects = new ArrayList<>();
    private final List<Player> players= new ArrayList<>();
    private OnePointObject collisionPoint = null;

    @PostConstruct
    public void init() {
        status = GameStatus.RUNNING;
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
