package com.aquip.tetris.ai.search;

import com.aquip.tetris.input.GameInput;
import com.aquip.tetris.placement.PlacementResult;
import com.aquip.tetris.state.MatchState;
import com.aquip.tetris.state.PlayerState;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

public class PlannedMatchState extends MatchState {

    private final UUID plannerId;
    private final Queue<GameInput> plannedInputs;
    private final PlacementResult placement;
    private final int linesCleared;
    private final int garbageSent;
    private final int scoreGained;
    private final int comboDepth;
    private final int b2bDepth;
    private final boolean usedHold;

    public PlannedMatchState(int tick,
                             int seed,
                             List<PlayerState> players,
                             UUID plannerId,
                             Queue<GameInput> plannedInputs,
                             PlacementResult placement,
                             int linesCleared,
                             int garbageSent,
                             int scoreGained,
                             int comboDepth,
                             int b2bDepth,
                             boolean usedHold) {
        super(tick, seed, players);
        this.plannerId = plannerId;
        this.plannedInputs = new ArrayDeque<>(plannedInputs);
        this.placement = placement;
        this.linesCleared = linesCleared;
        this.garbageSent = garbageSent;
        this.scoreGained = scoreGained;
        this.comboDepth = comboDepth;
        this.b2bDepth = b2bDepth;
        this.usedHold = usedHold;
    }

    public UUID plannerId() {
        return plannerId;
    }

    public Queue<GameInput> plannedInputs() {
        return new ArrayDeque<>(plannedInputs);
    }

    public PlacementResult placement() {
        return placement;
    }

    public int linesCleared() {
        return linesCleared;
    }

    public int garbageSent() {
        return garbageSent;
    }

    public int scoreGained() {
        return scoreGained;
    }

    public int comboDepth() {
        return comboDepth;
    }

    public int b2bDepth() {
        return b2bDepth;
    }

    public boolean usedHold() {
        return usedHold;
    }
}
