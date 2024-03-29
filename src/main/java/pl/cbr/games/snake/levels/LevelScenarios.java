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
        levels.add(Level.of(5,1,0,0,0));
        levels.add(Level.of(30,4,1,0,0));
        levels.add(Level.of(50,5,5,0,1));
        levels.add(Level.of(50,5,10,1,2));
        levels.add(Level.of(30,5,20,2,3));

        levels.add(Level.of(30,10,20,3,3));
        levels.add(Level.of(40,15,20,4,2));
        levels.add(Level.of(50,20,20,5,3));
        levels.add(Level.of(60,25,20,6,3));
        levels.add(Level.of(70,20,20,7,3));
        levels.add(Level.of(80,35,20,8,1));

        levels.add(Level.of(50,15,50,3,1));
        levels.add(Level.of(60,100,0,1,1));
        levels.add(Level.of(100,100,0,1,1));
        levels.add(Level.of(500,100,0,1,0));
        levels.add(Level.of(500,100,0,0,0));
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
