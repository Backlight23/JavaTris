package com.aquip.tetris.ai;

import com.aquip.tetris.input.PlayerInput;
import com.aquip.tetris.player.Player;

public class SimpleAI implements AIController {

    @Override
    public PlayerInput decide(Player player) {

        PlayerInput input = new PlayerInput();
        input.player = player;

        // No actions for now (AI does nothing)
        return input;
    }
}