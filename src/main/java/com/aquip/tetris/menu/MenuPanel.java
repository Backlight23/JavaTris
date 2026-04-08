package com.aquip.tetris.menu;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private MenuState state;

    public void setState(MenuState state) {
        this.state = state;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == null) return;

        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 36));
        g2.drawString("TETRIS", 300, 100);

        // Menu
        g2.setFont(new Font("Monospaced", Font.PLAIN, 20));

        if (state.screen == MenuOption.PLAY) {
            drawPlayMenu(g2);
        } else if (state.screen == MenuOption.GAME_SETTINGS) {
            g2.drawString("Game Settings (not implemented)", 200, 200);
        } else if (state.screen == MenuOption.OPTIONS) {
            g2.drawString("Options (not implemented)", 200, 200);
        }
    }

    private void drawPlayMenu(Graphics2D g2) {

        String[] options = {
                "Start Game",
                "Players: " + state.playerCount,
                "Game Settings",
                "Options"
        };

        int startY = 200;

        for (int i = 0; i < options.length; i++) {

            if (i == state.selectionIndex) {
                g2.setColor(Color.YELLOW);
                g2.drawString("> " + options[i], 250, startY + i * 40);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString("  " + options[i], 250, startY + i * 40);
            }
        }
    }
}