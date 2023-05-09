package pl.cbr.games.snake.objects.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.cbr.games.snake.BoardModel;
import pl.cbr.games.snake.GameResource;
import pl.cbr.games.snake.ResourceLoader;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;
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

    public Player(BoardModel boardModel, PlayerConfig playerConfig, GameConfig gameConfig, ResourceLoader resourceLoader) {
        super(gameConfig, boardModel, resourceLoader);
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
            return Optional.of(new PlayerObject(gameConfig, resourceLoader, null));
        }
        Rectangle boardRectangle = new Rectangle(new Point(),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
        if (getPlayerModel().isOutside(boardRectangle)) {
            getPlayerState().setInGame(false);
            return Optional.of(new RectObject(gameConfig, resourceLoader, null));
        }
        return Optional.empty();
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }

    @Override
    public void doDrawing(Graphics g) {
        Image ball = isBot() ? resourceLoader.get(GameResource.BALL0) : resourceLoader.get(GameResource.BALL1);
        for (int z = 0; z < getPlayerModel().getViewSize(); z++) {
            Point point = getPlayerModel().get(z).multiply(gameConfig.getDotSize());
            if (z == 0) {
                g.drawImage(resourceLoader.get(GameResource.HEAD),  point.getX(), point.getY(), null);
            } else {
                g.drawImage(ball, point.getX(), point.getY(), null);
            }
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
