package pl.cbr.games.snake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.cbr.games.snake.config.ResourcesConfig;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class ResourceLoader {

    private final Map<GameResource, Image> resources = new HashMap<>();

    private final ResourcesConfig resourcesConfig;

    public ResourceLoader(ResourcesConfig resourcesConfig) {
        this.resourcesConfig = resourcesConfig;
        loadImages();
    }

    private static final String IMAGES_DIR = "data/";

    private void loadImages() {
        try {
            Class<?> cls = Class.forName(getClass().getName());
            var cLoader = cls.getClassLoader();
            resources.put(GameResource.BALL0, getImage(cLoader, resourcesConfig.getBall0()));
            resources.put(GameResource.BALL1, getImage(cLoader, resourcesConfig.getBall1()));
            resources.put(GameResource.APPLE, getImage(cLoader, resourcesConfig.getApple()));
            resources.put(GameResource.HEAD, getImage(cLoader,resourcesConfig.getHead()));
            resources.put(GameResource.WALL,getImage(cLoader,resourcesConfig.getWall()));
            resources.put(GameResource.LEMON,getImage(cLoader,resourcesConfig.getLemon()));
            resources.put(GameResource.START_LOGO, getBufferedImage(cLoader,resourcesConfig.getStartLogo()));
        } catch (ClassNotFoundException | IOException e) {
            log.error("{} ",e.getMessage(), e);
        }
    }

    public Image get(GameResource gameResource) {
        return resources.get(gameResource);
    }


    public void playSound(String name) {
        String soundName = IMAGES_DIR+name+".wav";
        try {
            Class<?> cls = Class.forName(getClass().getName());
            var cLoader = cls.getClassLoader();
            URL url = cLoader.getResource(soundName);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | ClassNotFoundException | IOException | LineUnavailableException e) {
            log.error("Problem with read audio file",e);
        }
    }
    private Image getImage(ClassLoader cLoader, String resourceName) {
        log.debug("read: {}", cLoader.getResource(IMAGES_DIR+resourceName));
        return (new ImageIcon(Objects.requireNonNull(
                cLoader.getResource(IMAGES_DIR+resourceName)))).getImage();
    }

    private BufferedImage getBufferedImage(ClassLoader cLoader, String resourceImage) throws ClassNotFoundException, IOException {
        log.debug("read: {}", cLoader.getResource(IMAGES_DIR + resourceImage));
        return ImageIO.read(cLoader.getResource(IMAGES_DIR + resourceImage));
    }
}
