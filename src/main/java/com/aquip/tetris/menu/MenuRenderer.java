package com.aquip.tetris.menu;

public class MenuRenderer {

    public void render(MenuState state) {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("==== TETRIS ====");
        System.out.println();

        if (state.screen == MenuOption.PLAY) {
            renderPlayScreen(state);
        } else if (state.screen == MenuOption.GAME_SETTINGS) {
            renderGameSettings(state);
        } else if (state.screen == MenuOption.OPTIONS) {
            renderOptions(state);
        }

        System.out.println();
        System.out.println("Use arrow keys + Enter");
    }

    private void renderPlayScreen(MenuState state) {

        String[] options = {
                "Start Game",
                "Players: " + state.playerCount,
                "Game Settings",
                "Options"
        };

        for (int i = 0; i < options.length; i++) {
            if (i == state.selectionIndex) {
                System.out.println("> " + options[i]);
            } else {
                System.out.println("  " + options[i]);
            }
        }
    }

    private void renderGameSettings(MenuState state) {
        System.out.println("== GAME SETTINGS ==");
        System.out.println("(not implemented)");
    }

    private void renderOptions(MenuState state) {
        System.out.println("== OPTIONS ==");
        System.out.println("(not implemented)");
    }
}