package com.aquip.tetris.engine.handler;

import com.aquip.tetris.engine.TickContext;
import com.aquip.tetris.state.PlayerState;

import static java.lang.Integer.min;

public class GravityHandler implements PlayerHandler {

    @Override
    public void apply(PlayerState player, TickContext context) {

        if (!player.piece.hasPiece()) return;

        var ctx = context.get(player);

        player.gravity.gravityTicks++;

        int threshold = player.config.gravityTick;

        if (player.gravity.gravityTicks >= threshold ||
                (ctx.softDrop && player.gravity.gravityTicks >= min(2, threshold))) {

            player.gravity.gravityTicks = 0;

            ctx.moveY += 1;
        }
    }
}