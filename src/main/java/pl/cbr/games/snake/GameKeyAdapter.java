package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@AllArgsConstructor
@Slf4j
public class GameKeyAdapter extends KeyAdapter {

    private final Game game;
    private final GameModel model;

    @Override
    public void keyPressed(KeyEvent e) {
        model.getPlayers().forEach(player -> player.keyPressed(e));
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R -> startLogic();
            case KeyEvent.VK_P -> pauseLogic();
        }
        log.debug("{} key",(new StringBuffer()).append(e.getKeyChar()));
    }

    private void startLogic() {
        model.setStatus(GameStatus.RUNNING);
        game.initGame();
    }

    private void pauseLogic() {
        switch(model.getStatus()) {
            case PAUSED -> model.setStatus(GameStatus.RUNNING);
            case RUNNING -> model.setStatus(GameStatus.PAUSED);
        }
    }
}
