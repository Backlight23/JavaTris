package com.aquip.tetris.menu;

public class MenuState {

    public MenuOption screen;

    // current selected index in menu
    public int selectionIndex;

    // example: used in PLAY screen
    public int playerCount;

    public MenuState() {
        this.screen = MenuOption.PLAY;
        this.selectionIndex = 0;
        this.playerCount = 1;
    }
}