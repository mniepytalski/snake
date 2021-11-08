package pl.cbr.games.snake.config.control;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class PlayerControlTool {
    private final Map<String,Integer> controlKeys = new HashMap<>();

    public PlayerControlTool() {
        Field[] fields = java.awt.event.KeyEvent.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && Modifier.isPublic(f.getModifiers()))  {
                try {
                    controlKeys.put(f.getName(),f.getInt(f.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Integer getCode(String keyName) {
        return controlKeys.get(keyName);
    }
}
