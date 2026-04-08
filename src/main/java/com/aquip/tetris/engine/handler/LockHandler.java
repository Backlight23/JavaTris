package com.aquip.tetris.engine.handler;

import com.aquip.tetris.engine.TickContext;
import com.aquip.tetris.state.PlayerState;

public class LockHandler implements PlayerHandler {

    @Override
    public void apply(PlayerState player, TickContext context) {

        if (!player.piece.hasPiece()) return;

        var ctx = context.get(player);
        var lock = player.lock;
        var config = player.config;
        //System.out.println(ctx.pieceGrounded);
        boolean grounded = ctx.pieceGrounded;

        if (ctx.hardDrop) {
            ctx.pieceLocked = true;
            return;
        }

        if (!grounded) {
            lock.lockTicks = 0;
            return;
        }

        boolean reset = false;

        if (ctx.pieceMoved) {

            if (ctx.moveX != 0 && lock.slides < config.maxSlides) {
                lock.slides++;
                reset = true;
            }

            if (ctx.rotation != 0 && lock.rotations < config.maxRotations) {
                lock.rotations++;
                reset = true;
            }
        }

        if (reset) {
            lock.lockTicks = 0;
        } else {
            lock.lockTicks++;
        }

        if (lock.lockTicks >= config.lockTick) {
            ctx.pieceLocked = true;
        }

        // =====================
        // 4. Lock condition
        // =====================
        if (lock.lockTicks >= config.lockTick) {
            ctx.pieceLocked = true;
        }
    }

    private void resetLock(com.aquip.tetris.state.LockState lock) {
        lock.lockTicks = 0;
        lock.slides = 0;
        lock.rotations = 0;
        lock.hardDrop = false;
    }
}