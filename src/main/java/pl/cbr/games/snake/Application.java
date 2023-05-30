package pl.cbr.games.snake;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import pl.cbr.games.snake.config.Messages;

import javax.swing.*;

@AllArgsConstructor
@SpringBootApplication
public class Application {

    private final Game game;
    private final Messages messages;

    @Bean
    public JFrame frame() {
        JFrame frame = new JFrame(messages.getTitle());
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        return frame;
    }

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false);
        builder.run(args);
    }
}