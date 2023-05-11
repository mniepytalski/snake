package pl.cbr.games.snake;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.player.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class BoardModel {
    private final List<OnePointObject> objects = new ArrayList<>();
    private final List<Player> players= new ArrayList<>();


    public void addPlayer(Player player) {
        getPlayers().add(player);
    }

    public void clearBots() {
        List<Player> livePlayers = getPlayers().stream().filter(player -> !player.isBot()).toList();
        getPlayers().clear();
        getPlayers().addAll(livePlayers);
    }
}
