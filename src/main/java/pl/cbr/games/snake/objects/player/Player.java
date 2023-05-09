package pl.cbr.games.snake.objects.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.geom2d.Rectangle;
import pl.cbr.games.snake.gfx.GameGraphics;
import pl.cbr.games.snake.objects.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Player extends OnePointObject {

    PlayerState playerState;
    private final PlayerModel playerModel;
    private final GameGraphics gfx;

    public Player(BoardModel boardModel, PlayerConfig playerConfig, GameConfig gameConfig, GameGraphics gfx) {
        super(gameConfig, boardModel, gfx);
        this.gfx = gfx;
        playerModel = new PlayerModel(gameConfig, playerConfig);
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
            return Optional.of(new PlayerObject(gameConfig, null, gfx));
        }
        Rectangle boardRectangle = new Rectangle(new Point2D(),
                (new Point2D(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
        if (getPlayerModel().isOutside(boardRectangle)) {
            getPlayerState().setInGame(false);
            return Optional.of(new RectObject(gameConfig,  null, gfx));
        }
        return Optional.empty();
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }

    @Override
    public void doDrawing(Graphics g) {
        GameResource ballResource = isBot() ? GameResource.BALL0 : GameResource.BALL1;
        Point2D startPoint = getPlayerModel().get(0).multiply(gameConfig.getDotSize());
        gfx.drawImage(g, GameResource.HEAD,  startPoint);
        for (int z = 1; z < getPlayerModel().getViewSize(); z++) {
            Point2D point = getPlayerModel().get(z).multiply(gameConfig.getDotSize());
            gfx.drawImage(g, ballResource, point);
        }
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
