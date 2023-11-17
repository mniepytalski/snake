package pl.cbr.games.snake.gfx;

import lombok.Data;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.*;
import pl.cbr.games.snake.config.GameConfig;
import pl.cbr.games.snake.config.MessagesConfig;
import pl.cbr.games.snake.geom2d.Point2D;
import pl.cbr.games.snake.objects.player.Player;
import pl.cbr.games.snake.objects.player.PlayerModel;

import java.awt.*;

@Component
@Data
public class BoardGraphics {

    private final GameConfig gameConfig;
    private final GameModel model;
    private final MessagesConfig messages;
    private final GameGraphics gfx;

    private static final String FONT_TYPE = "Courier";

    int counter = 0;


    public void drawLattice(Graphics g) {
        g.setColor(Color.GRAY);
        for ( int x=gameConfig.getDotSize(); x<=gameConfig.getWidth(); x+=gameConfig.getDotSize()) {
            g.drawLine(x, 0, x, gameConfig.getHeight());
        }
        for ( int y=gameConfig.getDotSize(); y<=gameConfig.getHeight(); y+=gameConfig.getDotSize()) {
            g.drawLine(0, y, gameConfig.getWidth(), y);
        }
    }

    public void printBoard(GameStatus gameStatus, Graphics g, Game game) {
        switch(gameStatus) {
            case START_LOGO -> printStartLogo(g, game);
            case RUNNING -> printRunningBoard(g, game);
            case STOP -> gameOver(g, game);
            case PAUSED -> gamePaused(g, game);
            case NEXT_LEVEL -> nextLevel(g, game);
        }
    }

    private void printStartLogo(Graphics g, Game game) {
        gfx.drawImage(g, GameResource.START_LOGO, Point2D.of(0, 0));
        printCenterText(g, game, Color.white, messages.getStartGame());
    }
    private void printRunningBoard(Graphics g, Game game) {
        model.getObjects().forEach(objectToDraw -> gfx.drawOnePointObject(g, objectToDraw));
        model.getPlayers().forEach(objectToDraw -> gfx.drawPlayer(g, objectToDraw));
        if ( gameConfig.isLattice()) {
            drawLattice(g);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(format(messages.getLevelInfo(), (game.getLevelScenarios().getActualLevel()+1)), 80, 14);
        g.drawString(format(messages.getAllPointsToFinish(), game.getLevelScenarios().getLevel().getPointsToFinish()), 140, 14);

        int id = 1;
        for (Player player : model.getPlayers()) {
            printPoints(player.getPlayerModel(), g,id++);
        }
    }

    private void printPoints(PlayerModel playerModel, Graphics g, int id) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(playerModel.getName()+": "+playerModel.getPoints(),10,14*id);
    }
    private void gameOver(Graphics g, Game game) {
        printRunningBoard(g, game);
        if (model.getCollisionPoint() != null) {
            if ( counter++%2==0 ) gfx.drawOnePointObject(g, model.getCollisionPoint());
        }
        printCenterText(g, game, Color.white, messages.getEndGame());
    }

    private void nextLevel(Graphics g, Game game) {
        printRunningBoard(g, game);
        String message = format(messages.getNextLevel(), game.getLevelScenarios().getActualLevel());
        printCenterText(g, game, Color.white, message);
    }

    private void gamePaused(Graphics g, Game game) {
        printRunningBoard(g, game);
        printCenterText(g, game, Color.cyan, messages.getPausedGame());
    }

    private void printCenterText(Graphics g, Game game, Color color, String text) {
        Font small = new Font(FONT_TYPE, Font.BOLD, 24);
        FontMetrics fontMetrics = game.getFontMetrics(small);
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
