package com.aquip.tetris.ai;

import com.aquip.tetris.input.PlayerInput;
import com.aquip.tetris.player.Player;

public interface AIController {

    /**
     * Decide what input the AI should perform this tick.
     * For now, returns an empty input (no-op).
     */
    PlayerInput decide(Player player);
}