package com.aquip.tetris.engine.handler;

import com.aquip.tetris.engine.TickContext;
import com.aquip.tetris.engine.PlayerTickContext;
import com.aquip.tetris.state.PlayerState;
import com.aquip.tetris.piece.Piece;
import com.aquip.tetris.piece.PieceRegistry;
import com.aquip.tetris.placement.PlacementResult;
import com.aquip.tetris.placement.SpinDetector;
import com.aquip.tetris.placement.SpinResult;

public class PlacementHandler implements PlayerHandler {

    @Override
    public void apply(PlayerState player, TickContext context) {

        PlayerTickContext ctx = context.get(player);

        if (!ctx.pieceLocked) return;
        if (!player.piece.hasPiece()) return;

        player.next.canHold = true;

        Piece piece = player.piece.currentPiece;
        int[][] board = player.board.board;

        var registry = PieceRegistry.getInstance();
        int[][] shape = registry.getRotation(piece.type, piece.rotation);

        // =====================
        // 1. SPIN DETECTION
        // =====================
        SpinResult spin = SpinDetector.detectSpin(player.board.board, piece, ctx.moveContext.rotated);

        // =====================
        // 2. PLACE PIECE
        // =====================
        placePiece(board, piece, shape);

        // =====================
        // 3. CLEAR LINES
        // =====================
        int linesCleared = clearLines(board);

        // =====================
        // 4. RESULT
        // =====================
        PlacementResult result = new PlacementResult(
                piece.type,
                spin,
                piece.x,
                piece.y,
                piece.rotation,
                linesCleared
        );

        player.time.addPlacement(result);

        ctx.piecePlaced = true;

        player.piece.currentPiece = null;
        ctx.pieceSpawned = true;
    }
    // =====================
    // PLACE PIECE
    // =====================
    private void placePiece(int[][] board, Piece piece, int[][] shape) {

        int pieceValue = piece.type.ordinal() + 1;

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {

                if (shape[y][x] == 0) continue;

                int bx = piece.x + x;
                int by = piece.y + y;

                if (by >= 0 && by < board.length &&
                        bx >= 0 && bx < board[0].length) {
                    board[by][bx] = pieceValue;
                }
            }
        }
    }

    // =====================
    // LINE CLEARING
    // =====================
    private int clearLines(int[][] board) {

        int height = board.length;
        int width = board[0].length;

        int[][] newBoard = new int[height][width];

        int writeY = height - 1;
        int linesCleared = 0;

        for (int y = height - 1; y >= 0; y--) {

            boolean full = true;

            for (int x = 0; x < width; x++) {
                if (board[y][x] == 0) {
                    full = false;
                    break;
                }
            }

            if (full) {
                linesCleared++;
            } else {
                newBoard[writeY] = board[y].clone();
                writeY--;
            }
        }

        while (writeY >= 0) {
            newBoard[writeY] = new int[width];
            writeY--;
        }

        for (int y = 0; y < height; y++) {
            board[y] = newBoard[y];
        }

        return linesCleared;
    }
}
