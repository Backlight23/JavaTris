package com.aquip.tetris.ai;

import com.aquip.tetris.ai.eval.EvaluationFunction;
import com.aquip.tetris.ai.planner.InputPlanner;
import com.aquip.tetris.ai.search.FutureStateGenerator;
import com.aquip.tetris.input.GameInput;
import com.aquip.tetris.input.PlayerInput;
import com.aquip.tetris.player.Player;
import com.aquip.tetris.state.MatchState;
import com.aquip.tetris.state.PlayerState;

import java.util.*;

public class PlanningAI implements AIController {

    private final FutureStateGenerator generator;
    private final EvaluationFunction evaluator;
    private final InputPlanner planner;

    /**
     * How many ticks must pass between each input being sent.
     * 0 = one input per tick (original behaviour).
     * 1 = one input every 2 ticks, etc.
     */
    private final int tickDelay;
    private int ticksSinceLastInput;

    private final Queue<GameInput> inputQueue = new ArrayDeque<>();
    private String currentPlanKey;

    /** Convenience constructor – preserves original zero-delay behaviour. */
    public PlanningAI(FutureStateGenerator generator,
                      EvaluationFunction evaluator,
                      InputPlanner planner) {
        this(generator, evaluator, planner, 0);
    }

    public PlanningAI(FutureStateGenerator generator,
                      EvaluationFunction evaluator,
                      InputPlanner planner,
                      int tickDelay) {
        if (tickDelay < 0) throw new IllegalArgumentException("tickDelay must be >= 0");
        this.generator = generator;
        this.evaluator = evaluator;
        this.planner = planner;
        this.tickDelay = tickDelay;
        this.ticksSinceLastInput = tickDelay; // ready to fire on tick 0
    }

    @Override
    public PlayerInput decide(Player player, MatchState state) {

        PlayerInput input = new PlayerInput();
        input.player = player;
        input.inputs = Collections.emptySet();

        PlayerState playerState = state == null ? null : state.getPlayerState(player);
        if (playerState == null || !playerState.status.alive || !playerState.piece.hasPiece()) {
            inputQueue.clear();
            currentPlanKey = null;
            ticksSinceLastInput = tickDelay; // reset so we're ready on respawn
            return input;
        }

        String planKey = buildPlanKey(playerState);
        if (!planKey.equals(currentPlanKey)) {
            inputQueue.clear();
            currentPlanKey = planKey;
            ticksSinceLastInput = tickDelay; // new piece → fire immediately
        }

        if (inputQueue.isEmpty()) {
            computeNewPlan(player, state);
        }

        // Only consume the next input once enough ticks have elapsed.
        if (ticksSinceLastInput >= tickDelay) {
            GameInput next = inputQueue.poll();
            if (next != null) {
                input.inputs = Set.of(next);
            }
            ticksSinceLastInput = 0;
        } else {
            ticksSinceLastInput++;
        }

        return input;
    }

    private void computeNewPlan(Player player, MatchState state) {
        List<MatchState> futures = generator.generate(state);
        if (futures.isEmpty()) return;

        MatchState bestState = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (MatchState s : futures) {
            double score = evaluator.evaluate(s);
            if (score > bestScore) {
                bestScore = score;
                bestState = s;
            }
        }

        if (bestState == null) return;

        Queue<GameInput> plannedInputs = planner.plan(state, bestState);
        if (plannedInputs != null) {
            inputQueue.clear();
            inputQueue.addAll(plannedInputs);
        }
    }

    private String buildPlanKey(PlayerState playerState) {
        var piece = playerState.piece.currentPiece;
        return piece.type + ":" + playerState.time.amount() + ":" + playerState.next.canHold + ":" + playerState.next.held;
    }
}