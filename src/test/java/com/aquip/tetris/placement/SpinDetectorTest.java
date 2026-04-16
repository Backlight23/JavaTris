package com.aquip.tetris.placement;

import com.aquip.tetris.piece.Piece;
import com.aquip.tetris.piece.PieceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpinDetectorTest {

    @Test
    void detectsTSpinWhenFrontCornersAndThreeCornersAreOccupied() {
        int[][] board = new int[6][6];
        board[1][1] = 1;
        board[1][3] = 1;
        board[3][1] = 1;

        Piece piece = new Piece(PieceType.T, 0, 1, 1);

        assertEquals(SpinResult.T_SPIN, SpinDetector.detectSpin(board, piece, true));
        assertEquals(SpinResult.NONE, SpinDetector.detectSpin(board, piece, false));
    }
}
