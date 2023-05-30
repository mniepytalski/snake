package pl.cbr.games.snake.objects.player;

import lombok.Data;
import pl.cbr.games.snake.config.ControlConfig;
import pl.cbr.games.snake.config.control.PlayerConfigMapper;
import pl.cbr.games.snake.config.control.PlayerControlConfiguration;

import java.awt.event.KeyEvent;

@Data
public class PlayerState {

    private MoveDirection direction;
    private boolean inGame = true;

    private PlayerControlConfiguration playerControlConfiguration;

    private boolean controls;

    public PlayerState() {
        initState();
        controls = false;
    }

    public PlayerState(ControlConfig controlConfig) {
        PlayerConfigMapper playerConfigMapper = new PlayerConfigMapper();
        this.playerControlConfiguration = playerConfigMapper.map(controlConfig);
        initState();
        controls = true;
    }

    public void initState() {
        direction = MoveDirection.RIGHT;
        inGame = true;
    }

    void keyPressed(KeyEvent e) {
        if ( !controls ) {
            return;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getLeftKey()) && (getDirection()!=MoveDirection.RIGHT)) {
            direction = MoveDirection.LEFT;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getRightKey()) && (getDirection()!=MoveDirection.LEFT)) {
            direction = MoveDirection.RIGHT;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getUpKey()) && (getDirection()!=MoveDirection.DOWN)) {
            direction = MoveDirection.UP;
        }
        if ((e.getKeyCode() == playerControlConfiguration.getDownKey()) && (getDirection()!=MoveDirection.UP)) {
            direction = MoveDirection.DOWN;
        }
    }

    public void oppositeDirection() {
        switch(getDirection()) {
            case RIGHT -> setDirection(MoveDirection.LEFT);
            case DOWN -> setDirection(MoveDirection.UP);
            case LEFT -> setDirection(MoveDirection.RIGHT);
            case UP -> setDirection(MoveDirection.DOWN);
        }
    }

    public void turnLeft(MoveDirection startDirection) {
        this.direction = startDirection;
        switch(getDirection()) {
            case RIGHT -> setDirection(MoveDirection.UP);
            case DOWN -> setDirection(MoveDirection.RIGHT);
            case LEFT -> setDirection(MoveDirection.DOWN);
            case UP -> setDirection(MoveDirection.LEFT);
        }
    }

    public void turnRight() {
        switch(getDirection()) {
            case RIGHT -> setDirection(MoveDirection.DOWN);
            case DOWN -> setDirection(MoveDirection.LEFT);
            case LEFT -> setDirection(MoveDirection.UP);
            case UP -> setDirection(MoveDirection.RIGHT);
        }
    }
}
