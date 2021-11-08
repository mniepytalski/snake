package pl.cbr.games.snake.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "game")
public class GameConfig {
    private int width;
    private int height;
    private int dotSize;
    private int dotsOnStart;
    private boolean lattice;
    private int apples;
    private List<PlayerAndControlsConfig> players = new ArrayList<>();
}