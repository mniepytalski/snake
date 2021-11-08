package pl.cbr.games.snake.config;

import lombok.Data;

@Data
public class PlayerAndControlsConfig extends PlayerConfig {
    private ControlConfig control = new ControlConfig();
}
