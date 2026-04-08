package com.aquip.tetris.renderer;

import com.aquip.tetris.piece.*;
import com.aquip.tetris.state.PlayerState;

import java.awt.*;

public class HoldRenderer {

    private static final int BLOCK_SIZE = 16;

    public void render(Graphics2D g, PlayerState state, Rectangle area) {

        if (state.next.held == null) return;

        var registry = PieceRegistry.getInstance();

        g.setColor(state.next.canHold ? getColor(state.next.held) : Color.GRAY);

        for (Piece.Position p : registry.getBlocks(state.next.held, 0)) {
            g.fillRect(
                    area.x + p.x * BLOCK_SIZE,
                    area.y + p.y * BLOCK_SIZE,
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