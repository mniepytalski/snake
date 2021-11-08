package pl.cbr.games.snake.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "resources")
public class ResourcesConfig {
    private String ball0;
    private String ball1;
    private String apple;
    private String head;
    private String wall;
    private String lemon;
    private String startLogo;
}
