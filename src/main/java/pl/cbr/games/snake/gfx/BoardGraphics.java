package pl.cbr.games.snake.gfx;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.*;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.player.Player;

import java.awt.*;

@Component
@Data
public class BoardGraphics {

    private final GameConfig gameConfig;
    private final GameModel model;
    private final MessagesConfig messages;
    private final GameGraphics gfx;

    private static final String FONT_TYPE = "Courier";

    public void drawLattice(Graphics g) {
        g.setColor(Color.GRAY);
        for ( int x=gameConfig.getDotSize(); x<=gameConfig.getWidth(); x+=gameConfig.getDotSize()) {
            g.drawLine(x, 0, x, gameConfig.getHeight());
        }
        for ( int y=gameConfig.getDotSize(); y<=gameConfig.getHeight(); y+=gameConfig.getDotSize()) {
            g.drawLine(0, y, gameConfig.getWidth(), y);
        }
    }

    public void printBoard(GameStatus gameStatus, Graphics g, Board board) {
        switch(gameStatus) {
            case START_LOGO -> printStartLogo(g, board);
            case RUNNING -> printRunningBoard(g, board);
            case STOP -> gameOver(g, board);
            case PAUSED -> gamePaused(g, board);
            case NEXT_LEVEL -> nextLevel(g, board);
        }
    }

    private void printStartLogo(Graphics g, Board board) {
        gfx.drawImage(g, GameResource.START_LOGO, Point2D.of(0, 0));
        printCenterText(g, board, Color.white, messages.getStartGame());
    }

    private void printRunningBoard(Graphics g, Board board) {
        model.getObjects().forEach(objectToDraw -> gfx.drawOnePointObject(g, objectToDraw));
        model.getPlayers().forEach(objectToDraw -> gfx.drawPlayer(g, objectToDraw));
        if ( gameConfig.isLattice()) {
            drawLattice(g);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(format(messages.getLevelInfo(), (board.getLevelScenarios().getActualLevel()+1)), 80, 14);
        g.drawString(format(messages.getAllPointsToFinish(), board.getLevelScenarios().getLevel().getPointsToFinish()), 140, 14);

        int id = 1;
        for (Player player : model.getPlayers()) {
            player.printPoints(g,id++);
        }
    }

    int counter = 0;
    private void gameOver(Graphics g, Board board) {
        printRunningBoard(g, board);
        model.getCollisionPoint().ifPresent(p -> {
            if ( counter++%2==0 ) gfx.drawOnePointObject(g, p);
        });
        printCenterText(g, board, Color.white, messages.getEndGame());
    }

    private void nextLevel(Graphics g, Board board) {
        printRunningBoard(g, board);
        String message = format(messages.getNextLevel(),board.getLevelScenarios().getActualLevel());
        printCenterText(g, board, Color.white, message);
    }

    private void gamePaused(Graphics g, Board board) {
        printRunningBoard(g, board);
        printCenterText(g, board, Color.cyan, messages.getPausedGame());
    }

    private void printCenterText(Graphics g, Board board, Color color, String text) {
        Font small = new Font(FONT_TYPE, Font.BOLD, 24);
        FontMetrics fontMetrics = board.getFontMetrics(small);
        g.setColor(color);
        g.setFont(small);
        g.drawString(text,
                (gameConfig.getWidth() - fontMetrics.stringWidth(text)) / 2,
                gameConfig.getHeight() / 2);
    }

    private String format(String message, Object val) {
        return message.replaceFirst("\\{}",""+val);
    }
}
