package pl.cbr.games.snake.levels;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class LevelScenarios {
    private final List<Level> levels;
    private int actualLevel;

    public LevelScenarios() {
        actualLevel = 0;
        this.levels = new ArrayList<>();
        levels.add((new Level(20,1,0,0,0)));
        levels.add((new Level(30,4,1,0,0)));
        levels.add((new Level(50,5,5,0,0)));
        levels.add((new Level(50,5,10,1,0)));
        levels.add((new Level(30,5,20,2,1)));

        levels.add((new Level(30,10,20,3,1)));
        levels.add((new Level(40,15,20,4,1)));
        levels.add((new Level(50,20,20,5,1)));
        levels.add((new Level(60,25,20,6,1)));
        levels.add((new Level(70,20,20,7,1)));
        levels.add((new Level(80,35,20,8,1)));

        levels.add((new Level(50,5,100,15,1)));

        levels.add((new Level(200,200,0,5,1)));
    }

    public void setNextLevel() {
        actualLevel++;
    }

    public Level getLevel() {
        if (actualLevel>=levels.size()) {
            actualLevel = levels.size()-1;
        }
        return levels.get(actualLevel);
    }
}
