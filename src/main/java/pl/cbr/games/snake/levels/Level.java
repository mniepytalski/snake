package pl.cbr.games.snake.levels;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(staticName = "of")
@Value
public class Level {
    int pointsToFinish;
    int apples;
    int walls;
    int lemons;
    int bots;
}
