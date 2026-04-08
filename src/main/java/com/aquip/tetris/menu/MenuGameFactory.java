package com.aquip.tetris.menu;

import com.aquip.tetris.engine.GameEngine;
import com.aquip.tetris.engine.GameFactory;
import com.aquip.tetris.input.InputSource;
import com.aquip.tetris.player.PlayerType;
import com.aquip.tetris.state.ConfigState;
import com.aquip.tetris.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuGameFactory {

    public static GameEngine createGame(int playerCount, ConfigState baseConfig) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player(
                    UUID.randomUUID(),
                    PlayerType.HUMAN,
                    "P" + (i + 1)
            ));
        }

        return GameFactory.createGame(players, baseConfig);
    }
}