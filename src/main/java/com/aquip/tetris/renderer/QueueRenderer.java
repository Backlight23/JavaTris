package com.aquip.tetris.renderer;

import com.aquip.tetris.piece.*;
import com.aquip.tetris.state.PlayerState;

import java.awt.*;

public class QueueRenderer {

    private static final int BLOCK_SIZE = 16;
    private static final int SPACING = 50;

    public void render(Graphics2D g, PlayerState state, Rectangle area) {

        int i = 0;

        for (PieceType type : state.next.next) {
            if (i >= state.config.nextSize) break;

            drawMiniPiece(g, type, area.x, area.y + i * SPACING);
            i++;
        }
    }

    private void drawMiniPiece(Graphics2D g, PieceType type, int x, int y) {

        var registry = PieceRegistry.getInstance();

        g.setColor(getColor(type));

        for (Piece.Position p : registry.getBlocks(type, 0)) {
            g.fillRect(
                    x + p.x * BLOCK_SIZE,
                    y + p.y * BLOCK_SIZE,
                    BLOCK_SIZE,
                    BLOCK_SIZE
            );
        }
    }

    private Color getColor(PieceType type) {
        return switch (type) {
            case I -> Color.CYAN;
            case O -> Color.YELLOW;
            case T -> Color.MAGENTA;
            case S -> Color.GREEN;
            case Z -> Color.RED;
            case J -> Color.BLUE;
            case L -> Color.ORANGE;
        };
    }
}