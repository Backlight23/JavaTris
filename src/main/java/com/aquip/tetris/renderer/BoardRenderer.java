package com.aquip.tetris.renderer;

import com.aquip.tetris.piece.Piece;
import com.aquip.tetris.piece.PieceRegistry;
import com.aquip.tetris.state.PlayerState;

import java.awt.*;

public class BoardRenderer {

    private static final int BLOCK_SIZE = 24;

    public void render(Graphics2D g, PlayerState state, Rectangle area) {

        int boardWidth = state.board.getWidth();
        int boardHeight = state.board.getHeight();

        int boardPixelWidth = boardWidth * BLOCK_SIZE;
        int boardPixelHeight = boardHeight * BLOCK_SIZE;

        int offsetX = area.x + (area.width - boardPixelWidth) / 2;
        int offsetY = area.y + (area.height - boardPixelHeight) / 2;

        drawBoard(g, state, offsetX, offsetY);
        drawGhostPiece(g, state, offsetX, offsetY);
        drawCurrentPiece(g, state, offsetX, offsetY);
    }

    private void drawBoard(Graphics2D g, PlayerState state, int offsetX, int offsetY) {
        var board = state.board;

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {

                int value = board.get(x, y);

                if (value != 0) {
                    g.setColor(getColor(value));
                    fillCell(g, x, y, offsetX, offsetY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                    drawCell(g, x, y, offsetX, offsetY);
                }
            }
        }
    }

    private void drawCurrentPiece(Graphics2D g, PlayerState state, int offsetX, int offsetY) {
        if (!state.piece.hasPiece()) return;

        Piece piece = state.piece.currentPiece;

        g.setColor(getColor(piece.type.ordinal() + 1));

        for (Piece.Position p : piece.getBlocks(PieceRegistry.getInstance())) {
            fillCell(g, p.x, p.y, offsetX, offsetY);
        }
    }

    private void drawGhostPiece(Graphics2D g, PlayerState state, int offsetX, int offsetY) {
        if (!state.piece.hasPiece()) return;

        Piece ghost = computeGhost(state);

        g.setColor(new Color(255, 255, 255, 80));

        for (Piece.Position p : ghost.getBlocks(PieceRegistry.getInstance())) {
            fillCell(g, p.x, p.y, offsetX, offsetY);
        }
    }

    private Piece computeGhost(PlayerState state) {

        Piece piece = state.piece.currentPiece;
        Piece ghost = piece;

        while (!state.board.collides(ghost, PieceRegistry.getInstance())) {
            ghost = ghost.displace(0, 1);
        }

        // last valid position
        return ghost.displace(0, -1);
    }

    private void fillCell(Graphics2D g, int x, int y, int offsetX, int offsetY) {
        g.fillRect(offsetX + x * BLOCK_SIZE, offsetY + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }

    private void drawCell(Graphics2D g, int x, int y, int offsetX, int offsetY) {
        g.drawRect(offsetX + x * BLOCK_SIZE, offsetY + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }

    private Color getColor(int value) {
        return switch (value) {
            case 1 -> Color.CYAN;
            case 2 -> Color.YELLOW;
            case 3 -> Color.MAGENTA;
            case 4 -> Color.GREEN;
            case 5 -> Color.RED;
            case 6 -> Color.BLUE;
            case 7 -> Color.ORANGE;
            default -> Color.GRAY;
        };
    }
}