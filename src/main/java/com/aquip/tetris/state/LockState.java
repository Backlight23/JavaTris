package com.aquip.tetris.state;

public class LockState {

    public int lockTicks;
    public int slides;
    public int rotations;
    public boolean hardDrop;

    public LockState() {
        this.lockTicks = 0;
        this.slides = 0;
        this.rotations = 0;
        this.hardDrop = false;
    }

    public void reset() {
        lockTicks = 0;
        slides = 0;
        rotations = 0;
        hardDrop = false;
    }
}