package com.aquip.tetris.menu;

import com.aquip.tetris.engine.GameEngine;
import com.aquip.tetris.input.InputFrame;
import com.aquip.tetris.state.ConfigState;
import com.aquip.tetris.util.GameConfigParser;

import java.io.File;

public class MenuEngine {

    public final File gameConfigFile;
    public final File inputConfigFile;

    public final MenuState state;
    private final MenuInputMapper mapper;

    public MenuEngine(File gameConfigFile,
                      File inputConfigFile,
                      MenuInputMapper mapper) {

        this.gameConfigFile = gameConfigFile;
        this.inputConfigFile = inputConfigFile;
        this.state = new MenuState();
        this.mapper = mapper;
    }

    public GameEngine update(InputFrame frame) {

        for (MenuInput input : mapper.map(frame)) {
            if (input != null) {
                handleInput(input);
            }
        }

        return evaluateState();
    }

    private void handleInput(MenuInput input) {

        switch (input) {

            case UP -> state.selectionIndex = Math.max(0, state.selectionIndex - 1);

            case DOWN -> state.selectionIndex = Math.min(3, state.selectionIndex + 1);

            case LEFT -> {
                if (state.screen == MenuOption.PLAY) {
                    state.playerCount = Math.max(1, state.playerCount - 1);
                }
            }

            case RIGHT -> {
                if (state.screen == MenuOption.PLAY) {
                    state.playerCount++;
                }
            }

            case CONFIRM -> {
                if (state.screen == MenuOption.PLAY) {
                    state.screen = null; // start game
                }
            }

            case BACK -> {
                state.screen = MenuOption.PLAY;
            }
        }
    }

    private GameEngine evaluateState() {

        if (state.screen != null) return null;

        ConfigState config = GameConfigParser.parse(gameConfigFile);

        return MenuGameFactory.createGame(state.playerCount, config);
    }
}