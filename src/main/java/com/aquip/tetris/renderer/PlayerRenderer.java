package com.aquip.tetris.renderer;

import com.aquip.tetris.state.PlayerState;

import java.awt.*;

public class PlayerRenderer {

    private final BoardRenderer board = new BoardRenderer();
    private final QueueRenderer queue = new QueueRenderer();
    private final HoldRenderer hold = new HoldRenderer();
    private final InfoRenderer info = new InfoRenderer();

    public void render(Graphics2D g, PlayerState state, Rectangle area) {

        int sideWidth = 80;

        Rectangle boardArea = new Rectangle(
                area.x + sideWidth,
                area.y,
                area.width - sideWidth * 2,
                area.height
        );

        Rectangle leftArea = new Rectangle(
                area.x,
                area.y,
                sideWidth,
                area.height
        );

        Rectangle rightArea = new Rectangle(
                area.x + area.width - sideWidth,
                area.y,
                sideWidth,
                area.height
        );

        board.render(g, state, boardArea);
        hold.render(g, state, leftArea);
        queue.render(g, state, rightArea);
        info.render(g, state, area);
    }
}