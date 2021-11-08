package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
@AllArgsConstructor
@Service
public class MainBoard extends JPanel implements ActionListener, Drawing {

    private final SystemTimer systemTimer;
    private final SystemState systemState;

    @PostConstruct
    private void init() {
        systemTimer.init(this);
        systemTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        repaint();
    }

    @Override
    public void doDrawing(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(0,0,systemState.getStep(),100);
        systemState.setStep(systemState.getStep()+10);
    }

    @Override
    public void paintComponent(Graphics g) {
        log.info("paintComponent");
        doDrawing(g);
    }
}
