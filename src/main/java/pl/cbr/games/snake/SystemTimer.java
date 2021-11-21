package pl.cbr.games.snake;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.event.ActionListener;

@Service
public class SystemTimer {
    private static final int DELAY = 200;

    private Timer timer;

    public void init(ActionListener listener) {
        timer = new Timer(DELAY, listener);
    }

    public void start() {
        if ( !timer.isRunning() ) {
            timer.start();
        }
    }

    public void stop() {
        timer.stop();
    }
}
