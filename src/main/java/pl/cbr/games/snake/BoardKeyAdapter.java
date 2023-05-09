package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@AllArgsConstructor
@Slf4j
public class BoardKeyAdapter extends KeyAdapter {

    private final Board board;

    @Override
    public void keyPressed(KeyEvent e) {
        board.getBoardModel().getPlayers().forEach(player -> player.keyPressed(e));
        switch(e.getKeyCode()) {
            case KeyEvent.VK_R -> startLogic();
            case KeyEvent.VK_P -> pauseLogic();
        }
        log.debug("{} key",(new StringBuffer()).append(e.getKeyChar()));
    }

    private void startLogic() {
        board.getGameModel().setStatus(GameStatus.RUNNING);
        board.initGame();
    }

    private void pauseLogic() {
        switch(board.getGameModel().getStatus()) {
            case PAUSED -> board.getGameModel().setStatus(GameStatus.RUNNING);
            case RUNNING -> board.getGameModel().setStatus(GameStatus.PAUSED);
        }
    }
}
