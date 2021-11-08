package pl.cbr.games.snake;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Slf4j
public class BoardKeyAdapter extends KeyAdapter {

    private final Board board;
    public BoardKeyAdapter(Board board) {
        this.board = board;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        board.getBoardModel().getPlayers().forEach(player -> player.keyPressed(e));
        if ( e.getKeyCode() == KeyEvent.VK_R ) {
            board.setGameStatus(GameStatus.RUNNING);
            board.initGame();
        }
        if ( e.getKeyCode() == KeyEvent.VK_P ) pauseLogic();
        if ( e.getKeyCode() == KeyEvent.VK_B ) board.setDebug(!board.isDebug());
        log.debug("{} key, debug: {}",(new StringBuffer()).append(e.getKeyChar()), board.isDebug());
    }

    private void pauseLogic() {
        if ( GameStatus.PAUSED == board.getGameStatus()) {
            board.setGameStatus(GameStatus.RUNNING);
        } else {
            if (GameStatus.RUNNING == board.getGameStatus()) {
                board.setGameStatus(GameStatus.PAUSED);
            }
        }
    }
}
