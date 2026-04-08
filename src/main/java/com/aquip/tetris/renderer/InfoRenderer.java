package com.aquip.tetris.renderer;

import com.aquip.tetris.state.PlayerState;

import java.awt.*;

public class InfoRenderer {

    public void render(Graphics2D g, PlayerState state, Rectangle area) {

        g.setColor(Color.WHITE);

        int x = area.x + 10;
        int y = area.y + 20;

        g.drawString("Score: " + state.status.Score, x, y);
        y += 20;

        g.drawString("Combo: " + state.combo.amount(), x, y);
        y += 20;

        g.drawString("B2B: " + state.b2b.amount(), x, y);
        y += 20;

        g.drawString("Pieces: " + state.time.amount(), x, y);
        y += 20;

        g.drawString("Alive: " + state.status.alive, x, y);
        y += 20;

        // =====================
        // GARBAGE INFO
        // =====================
        g.drawString("Garbage: " + state.garbage.totalLines(), x, y);
        y += 20;

        // Debug queue (multi-line)
        String debug = state.garbage.debugString(
                state.time.amount(),
                state.time.tick
        );

        for (String line : debug.split("\n")) {
            g.drawString(line, x, y);
            y += 15;
        }
    }
}