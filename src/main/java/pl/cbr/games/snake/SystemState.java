package pl.cbr.games.snake;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class SystemState {
    private int step;
}
