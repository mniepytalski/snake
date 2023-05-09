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
            case KeyEvent.VK_B -> board.setDebug(!board.isDebug());
        }
        log.debug("{} key, debug: {}",(new StringBuffer()).append(e.getKeyChar()), board.isDebug());
    }

    private void startLogic() {
        board.setGameStatus(GameStatus.RUNNING);
        board.initGame();
    }

    private void pauseLogic() {
        switch(board.getGameStatus()) {
            case PAUSED -> board.setGameStatus(GameStatus.RUNNING);
            case RUNNING -> board.setGameStatus(GameStatus.PAUSED);
        }
    }
}
