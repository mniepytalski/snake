package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.config.PositionConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.geom2d.Square;
import pl.cbr.games.snake.levels.Level;
import pl.cbr.games.snake.objects.*;
import pl.cbr.games.snake.objects.player.BotPlayer;
import pl.cbr.games.snake.objects.player.mind.MoveStrategy;

import java.util.*;

@Slf4j
@Getter
@AllArgsConstructor
@Component
public class GameLogic {

    private final GameConfig gameConfig;
    private final Collision collision;
    private final GameModel model;
    private final MoveStrategy moveStrategy;

    public void initLevel(Level level) {
        model.initLevelObjects(level);

        model.clearBots();
        for (int i = 0; i < level.getBots(); i++) {
            model.addPlayer(new BotPlayer(gameConfig.getDotsOnStart(), new PlayerConfig("Bot" + i, new PositionConfig(2 + i * 5, 2 + i * 5)), moveStrategy));
        }
        List<Square> forbiddenAreas = model.initPlayers();

        tryingToChangeDuplicatePosition(forbiddenAreas);
        model.setCollisionPoint(null);
    }

    public boolean collisionWithPoint() {
        return !model.detectDuplicates().isEmpty();
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
            log.info("{} init objects:{}, duplicates:{}, forbidden:{}", attempt, model.getObjects().size(), duplicates, forbiddenAreasCount);
        }
    }

    private int getDuplicatesAndChangePosition() {
        Map<Point2D, List<OnePointObject>> duplicates = model.detectDuplicates();
        duplicates.forEach((k,v) -> v.stream().skip(1).forEach(o -> o.setRandomPosition(collision.getBoard().getRightBottom())));
        return duplicates.size();
    }

    private int changePositionFromForbiddenAreas(List<Square> forbiddenAreas) {
        List<OnePointObject> pointsToChange = model.getObjects().stream().filter(p ->
                forbiddenAreas.stream().anyMatch(area -> area.isInside(p.getPosition()))).toList();
        pointsToChange.forEach(o -> o.setRandomPosition(collision.getBoard().getRightBottom()));
        return pointsToChange.size();
    }
}
