package pl.cbr.games.snake.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "messages")
public class MessagesConfig {
    private String title;
    private String endGame;
    private String pausedGame;
    private String startGame;
    private String nextLevel;
    private String levelInfo;
    private String allPointsToFinish;
}
