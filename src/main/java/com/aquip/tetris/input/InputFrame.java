package com.aquip.tetris.input;

import java.util.HashSet;
import java.util.Set;

public class InputFrame {

    public final Set<Integer> pressed = new HashSet<>();
    public final Set<Integer> released = new HashSet<>();
    public final Set<Integer> held = new HashSet<>();
}