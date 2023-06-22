package pl.cbr.games.snake.levels;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(staticName = "of")
@Data
public class Level {
    private int pointsToFinish;
    private int apples;
    private int walls;
    private int lemons;
    private int bots;
}
