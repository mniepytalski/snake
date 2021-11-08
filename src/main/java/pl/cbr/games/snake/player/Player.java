package pl.cbr.games.snake.player;

import lombok.Data;
import pl.cbr.games.snake.Drawing;
import pl.cbr.games.snake.GameResources;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.PlayerConfig;
import pl.cbr.games.snake.geom2d.Point;
import pl.cbr.games.snake.geom2d.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;

@Data
public class Player implements Drawing {

    private final GameConfig gameConfig;
    private PlayerConfig playerConfig;
    private final GameResources gameResources;

	private int id;
    PlayerState playerState;
    private PlayerModel playerModel;

    private static int idGenerator = 1;

    public Player(PlayerConfig playerConfig, GameConfig gameConfig, GameResources gameResources) {
        this.id = idGenerator++;
        this.gameConfig = gameConfig;
        this.playerConfig = playerConfig;
        this.gameResources = gameResources;
        playerModel = new PlayerModel(gameConfig);
    }

    public void initPlayer() {
        playerModel.initPlayer(playerConfig.getPosition().getPoint());
        playerState.initState();
    }

    public void move() {
        getPlayerModel().move(getPlayerState().getDirection());
    }

    public boolean checkCollision() {
        if ( getPlayerModel().checkOurselfCollision() ) {
            getPlayerState().setInGame(false);
            return false;
        }
        Rectangle boardRectangle = new Rectangle(new Point(),
                (new Point(gameConfig.getWidth(),gameConfig.getHeight())).division(gameConfig.getDotSize()));
        if (getPlayerModel().isOutside(boardRectangle)) {
            getPlayerState().setInGame(false);
            return false;
        }
        return !getPlayerState().isInGame();
    }

    public void keyPressed(KeyEvent e) {
        getPlayerState().keyPressed(e);
    }

    @Override
    public void doDrawing(Graphics g) {
        for (int z = 0; z < getPlayerModel().getViewSize(); z++) {
            Point point = getPlayerModel().get(z).multiply(gameConfig.getDotSize());
            if (z == 0) {
                g.drawImage(gameResources.getHead(),  point.getX(), point.getY(), null);
            } else {
                g.drawImage(gameResources.getBall(getId()%2), point.getX(), point.getY(), null);
            }
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(playerConfig.getName()+": "+getPlayerModel().getPoints(),10,14*id);
    }
}
