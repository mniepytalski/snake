package pl.cbr.games.snake.config.control;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerControlConfiguration {
    private int leftKey;
    private int rightKey;
    private int upKey;
    private int downKey;
}
