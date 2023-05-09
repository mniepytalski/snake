package pl.cbr.games.snake.gfx;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.geom2d.Point2D;

import java.awt.*;

@AllArgsConstructor
@Component
public class GameGraphics {

    private final ResourceLoader resourceLoader;

    public void drawImage(Graphics g, GameResource resource, Point2D point) {
        g.drawImage(resourceLoader.get(resource), point.getX(), point.getY(), null);
    }
}
