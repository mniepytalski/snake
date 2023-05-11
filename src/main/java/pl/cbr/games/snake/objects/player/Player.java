package pl.cbr.games.snake.objects.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.cbr.games.snake.BoardLogic;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Collision;
import pl.cbr.games.snake.objects.OnePointObject;
import pl.cbr.games.snake.objects.PlayerObject;
import pl.cbr.games.snake.objects.RectObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Player extends OnePointObject {

    PlayerState playerState;
    private final PlayerModel playerModel;
    private final Collision collision;

    public Player(BoardLogic boardLogic, PlayerConfig playerConfig, Collision collision) {
        super(boardLogic);
        this.collision = collision;
        playerModel = new PlayerModel(boardLogic.getGameConfig(), playerConfig, collision);
    }

    public void init() {
        playerModel.initPlayer();
        playerState.initState();
    }

    public void move() {
        getPlayerModel().move(getPlayerState().getDirection());
    }

    public Optional<OnePointObject> checkCollision() {
        if ( getPlayerModel().checkOurselfCollision() ) {
            getPlayerState().setInGame(false);
            return Optional.of(new PlayerObject(null));
        }

        if (collision.isOutside(getPlayerModel().getHead())) {
            getPlayerState().setInGame(false);
            return Optional.of(new RectObject(null));
        }
        return Optional.empty();
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
    public void actionOnPlayerHit(PlayerModel playerModel) {

    }
}
