package pl.cbr.games.snake.objects.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.cbr.games.snake.GameModel;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.OnePointObject;

import java.awt.event.KeyEvent;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Player extends OnePointObject {

    PlayerState playerState;
    private final PlayerModel playerModel;

    public Player(int dotsOnStart, PlayerConfig playerConfig) {
        playerModel = new PlayerModel(dotsOnStart, playerConfig);
    }

    public void init() {
        playerModel.initPlayer();
        playerState.initState();
    }

    public void move() {
        getPlayerModel().move(getPlayerState().getDirection());
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }

    public boolean isBot() {
        return getName().indexOf("Bot")==0;
    }

    public String getName() {
        return getPlayerModel().getName();
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel, Collision collision, GameModel model) {

    }
}
