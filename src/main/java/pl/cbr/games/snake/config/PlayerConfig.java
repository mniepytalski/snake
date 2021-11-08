package pl.cbr.games.snake.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerConfig {
    String name;
    PositionConfig position = new PositionConfig();
}
