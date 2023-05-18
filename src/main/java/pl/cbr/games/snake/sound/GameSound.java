package pl.cbr.games.snake.sound;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.ResourceLoader;

@AllArgsConstructor
@Component
public class GameSound {
    private final ResourceLoader resource;

    public void playNextLevel() {
        runTask(() -> resource.playSound("nextLevel"));
    }

    public void playEating() {
        runTask(() -> resource.playSound("eating1"));
    }

    private void runTask(Runnable task) {
        Thread thread = new Thread(task);
        thread.start();
    }
}
