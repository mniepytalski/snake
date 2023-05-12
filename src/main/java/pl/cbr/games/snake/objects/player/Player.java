package pl.cbr.games.snake.objects.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.cbr.games.snake.GameLogic;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.objects.OnePointObject;

import java.awt.*;
import java.awt.event.KeyEvent;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Player extends OnePointObject {

    PlayerState playerState;
    private final PlayerModel playerModel;

    public Player(GameLogic gameLogic, PlayerConfig playerConfig) {
        super();
        playerModel = new PlayerModel(gameLogic.getGameConfig(), playerConfig);
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

    public void printPoints(Graphics g, int id) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(getPlayerModel().getName()+": "+getPlayerModel().getPoints(),10,14*id);
    }

    public boolean isBot() {
        return getName().indexOf("Bot")==0;
    }

    public String getName() {
        return getPlayerModel().getName();
    }

    @Override
    public void actionOnPlayerHit(PlayerModel playerModel, GameLogic gameLogic) {

    }
}
