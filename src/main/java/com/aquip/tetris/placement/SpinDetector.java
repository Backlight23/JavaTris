package com.aquip.tetris.placement;

import com.aquip.tetris.piece.Piece;
import com.aquip.tetris.piece.PieceType;

public final class SpinDetector {

    private SpinDetector() {
    }

    public static SpinResult detectSpin(int[][] board, Piece piece, boolean rotatedIntoPlace) {
        if (!rotatedIntoPlace || piece == null || piece.type != PieceType.T) {
            return SpinResult.NONE;
        }

        int cx = piece.x + 1;
        int cy = piece.y + 1;

        boolean[] corners = new boolean[4];
        corners[0] = isOccupied(board, cx - 1, cy - 1);
        corners[1] = isOccupied(board, cx + 1, cy - 1);
        corners[2] = isOccupied(board, cx - 1, cy + 1);
        corners[3] = isOccupied(board, cx + 1, cy + 1);

        int occupied = 0;
        for (boolean corner : corners) {
            if (corner) {
                occupied++;
            }
        }

        if (occupied < 3) {
            return SpinResult.NONE;
        }

        boolean front1;
        boolean front2;

        switch (piece.rotation & 3) {
            case 0 -> {
                front1 = corners[0];
                front2 = corners[1];
            }
            case 1 -> {
                front1 = corners[1];
                front2 = corners[3];
            }
            case 2 -> {
                front1 = corners[2];
                front2 = corners[3];
            }
            default -> {
                front1 = corners[0];
                front2 = corners[2];
            }
        }

        return front1 && front2 ? SpinResult.T_SPIN : SpinResult.T_SPIN_MINI;
    }

    private static boolean isOccupied(int[][] board, int x, int y) {
        if (x < 0 || x >= board[0].length || y < 0 || y >= board.length) {
            return true;
        }

        return board[y][x] != 0;
    }
}
