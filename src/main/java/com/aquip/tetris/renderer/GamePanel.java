package com.aquip.tetris.renderer;

import com.aquip.tetris.state.MatchState;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private MatchState state;

    private final BoardRenderer boardRenderer;

    public GamePanel() {
        this.boardRenderer = new BoardRenderer();
        setBackground(Color.BLACK);
    }

    public void setState(MatchState state) {
        this.state = state;
        repaint();
    }

    private final PlayerRenderer renderer = new PlayerRenderer();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state == null) return;

        Graphics2D g2 = (Graphics2D) g;

        int count = state.players.size();

        int cols = (int) Math.ceil(Math.sqrt(count));
        int rows = (int) Math.ceil((double) count / cols);

        int cellW = getWidth() / cols;
        int cellH = getHeight() / rows;

        for (int i = 0; i < count; i++) {
            int col = i % cols;
            int row = i / cols;

            Rectangle area = new Rectangle(
                    col * cellW,
                    row * cellH,
                    cellW,
                    cellH
            );

            renderer.render(g2, state.players.get(i), area);
        }
    }
}