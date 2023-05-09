package pl.cbr.games.snake;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
public class GameModel {
    private GameStatus status;

    @PostConstruct
    public void init() {
        status = GameStatus.RUNNING;
    }
}
