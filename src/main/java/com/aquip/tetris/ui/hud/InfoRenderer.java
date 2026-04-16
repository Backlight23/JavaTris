package com.aquip.tetris.ui.hud;

import com.aquip.tetris.state.PlayerState;

import java.awt.*;

public class InfoRenderer {

    public void render(Graphics2D g, PlayerState state, Rectangle area) {
        drawPanel(g, area, "Stats");

        g.setColor(Color.WHITE);
        int fontSize = Math.max(11, Math.min(15, Math.min(area.width / 9, area.height / 16)));
        int lineHeight = fontSize + 8;
        g.setFont(new Font("Monospaced", Font.PLAIN, fontSize));

        int x = area.x + 14;
        int y = area.y + 20 + lineHeight;

        g.drawString(state.player.getName(), x, y);
        y += lineHeight;

        g.drawString("Score: " + state.status.Score, x, y);
        y += lineHeight;

        g.drawString("Pieces: " + state.time.amount(), x, y);
        y += lineHeight;

        g.drawString("Gravity: " + state.config.gravityThresholdForPieces(state.time.amount()) + " ticks", x, y);
        y += lineHeight;

        g.drawString("Combo: " + state.combo.amount(), x, y);
        y += lineHeight;

        g.drawString("B2B: " + state.b2b.amount(), x, y);
        y += lineHeight;

        g.drawString("Garbage: " + state.garbage.totalLines(), x, y);
        y += lineHeight;

        g.drawString("Status: " + (state.status.alive ? "Playing" : "Game Over"), x, y);
    }

    private void drawPanel(Graphics2D g, Rectangle area, String title) {
        g.setColor(new Color(22, 26, 36, 220));
        g.fillRoundRect(area.x, area.y, area.width, area.height, 18, 18);
        g.setColor(new Color(110, 122, 150));
        g.drawRoundRect(area.x, area.y, area.width, area.height, 18, 18);
        g.setFont(new Font("Monospaced", Font.BOLD, 16));
        g.drawString(title, area.x + 12, area.y + 20);
    }
}
