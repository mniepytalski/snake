package pl.cbr.games.snake.config.control;

import pl.cbr.games.snake.config.ControlConfig;

public class PlayerConfigMapper {

    private PlayerControlTool playerControlTool;

    public PlayerConfigMapper() {
        this.playerControlTool = new PlayerControlTool();
    }

    public PlayerControlConfiguration map(ControlConfig controlConfig) {
        PlayerControlConfiguration config = new PlayerControlConfiguration();
        config.setDownKey(playerControlTool.getCode(controlConfig.getDown()));
        config.setUpKey(playerControlTool.getCode(controlConfig.getUp()));
        config.setLeftKey(playerControlTool.getCode(controlConfig.getLeft()));
        config.setRightKey(playerControlTool.getCode(controlConfig.getRight()));
        return config;
    }
}
