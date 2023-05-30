package pl.cbr.games.snake.gfx;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.Player;

import java.awt.*;

@AllArgsConstructor
@Component
public class GameGraphics {

    private final GameConfig gameConfig;
    private final ResourceLoader resourceLoader;

    public void drawOnePointObject(Graphics g, OnePointObject object) {
        drawImage(g, object.getImage(), object.getPosition());
    }

    public void drawPlayer(Graphics g, Player player) {
        GameResource ballResource = player.isBot() ? GameResource.BALL0 : GameResource.BALL1;
        drawImage(g, GameResource.HEAD,  player.getPlayerModel().get(0));
        for (int z = 1; z < player.getPlayerModel().getViewSize(); z++) {
            drawImage(g, ballResource, player.getPlayerModel().get(z));
        }
    }

    public void drawImage(Graphics g, GameResource resource, Point2D point) {
        Point2D position = point.multiply(gameConfig.getDotSize());
        drawImageRealDimension(g, resource, position);
    }

    //gfx.drawImageGameMetrics(g, getImage(), getPosition());

    private void drawImageRealDimension(Graphics g, GameResource resource, Point2D point) {
        g.drawImage(resourceLoader.get(resource), point.getX(), point.getY(), null);
    }
}
