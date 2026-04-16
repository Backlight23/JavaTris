package com.aquip.tetris.input;

import com.aquip.tetris.player.Player;
import com.aquip.tetris.player.PlayerType;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HumanInputSourceTest {

    @Test
    void secondPlayerUsesDedicatedMovementAndHardDropKeys() {
        Player player = new Player(UUID.randomUUID(), PlayerType.HUMAN, "NotP2");
        HumanInputSource source = new HumanInputSource(player, ControlScheme.PLAYER_TWO);

        InputFrame moveFrame = new InputFrame();
        moveFrame.pressed.add(KeyEvent.VK_A);
        moveFrame.held.add(KeyEvent.VK_A);

        PlayerInput moveInput = source.poll(moveFrame);
        assertTrue(moveInput.inputs.contains(GameInput.MOVE_LEFT));

        InputFrame hardDropFrame = new InputFrame();
        hardDropFrame.pressed.add(KeyEvent.VK_F);

        PlayerInput hardDropInput = source.poll(hardDropFrame);
        assertTrue(hardDropInput.inputs.contains(GameInput.HARD_DROP));
    }
}
