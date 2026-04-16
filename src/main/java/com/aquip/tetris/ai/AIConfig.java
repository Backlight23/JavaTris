package com.aquip.tetris.ai;

public class AIConfig {
    /** Ticks between each AI input. 0 = one input per tick. */
    public final int tickDelay;

    public AIConfig(int tickDelay) {
        if (tickDelay < 0) throw new IllegalArgumentException("tickDelay must be >= 0");
        this.tickDelay = tickDelay;
    }

    public static AIConfig defaults() {
        return new AIConfig(0);
    }
}