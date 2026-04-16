package com.aquip.tetris.ui.game;

import com.aquip.tetris.state.PlayerState;
import com.aquip.tetris.ui.hud.ControlsRenderer;
import com.aquip.tetris.ui.hud.HoldRenderer;
import com.aquip.tetris.ui.hud.InfoRenderer;
import com.aquip.tetris.ui.hud.QueueRenderer;

import java.awt.*;

public class PlayerRenderer {

    private final BoardRenderer board = new BoardRenderer();
    private final QueueRenderer queue = new QueueRenderer();
    private final HoldRenderer hold = new HoldRenderer();
    private final InfoRenderer info = new InfoRenderer();
    private final ControlsRenderer controls = new ControlsRenderer();

    public void render(Graphics2D g, PlayerState state, Rectangle area) {
        int padding = area.width < 760 ? 16 : 24;
        int gap = area.width < 760 ? 12 : 20;
        int columnGap = area.height < 460 ? 10 : 16;

        Rectangle contentArea = new Rectangle(
                area.x + padding,
                area.y + padding,
                area.width - padding * 2,
                area.height - padding * 2
        );

        int leftWidth = Math.max(92, Math.min(160, contentArea.width / 5));
        int rightWidth = Math.max(120, Math.min(180, contentArea.width / 4));
        int boardWidth = contentArea.width - leftWidth - rightWidth - gap * 2;

        if (boardWidth < 180) {
            int shortage = 180 - boardWidth;
            int leftTrim = Math.min(shortage / 2 + shortage % 2, Math.max(0, leftWidth - 84));
            leftWidth -= leftTrim;
            shortage -= leftTrim;

            int rightTrim = Math.min(shortage, Math.max(0, rightWidth - 108));
            rightWidth -= rightTrim;
            boardWidth = contentArea.width - leftWidth - rightWidth - gap * 2;
        }

        Rectangle leftColumn = new Rectangle(
                contentArea.x,
                contentArea.y,
                leftWidth,
                contentArea.height
        );

        Rectangle boardArea = new Rectangle(
                leftColumn.x + leftColumn.width + gap,
                contentArea.y,
                Math.max(180, boardWidth),
                contentArea.height
        );

        Rectangle rightColumn = new Rectangle(
                boardArea.x + boardArea.width + gap,
                contentArea.y,
                rightWidth,
                contentArea.height
        );

        int holdHeight = clamp(contentArea.height / 4, 84, area.width < 760 ? 104 : 120);
        int minControlsHeight = area.width < 760 ? 132 : 168;
        if (holdHeight + columnGap + minControlsHeight > contentArea.height) {
            holdHeight = Math.max(68, contentArea.height - columnGap - minControlsHeight);
        }
        int controlsTop = holdHeight + columnGap;
        int controlsHeight = Math.max(96, contentArea.height - controlsTop);

        int infoHeight = clamp(contentArea.height / 3, area.width < 760 ? 132 : 156, area.width < 760 ? 176 : 214);
        int minQueueHeight = area.width < 760 ? 96 : 120;
        if (infoHeight + columnGap + minQueueHeight > contentArea.height) {
            infoHeight = Math.max(112, contentArea.height - columnGap - minQueueHeight);
        }
        int queueTop = infoHeight + columnGap;
        int queueHeight = Math.max(88, contentArea.height - queueTop);

        Rectangle holdArea = new Rectangle(leftColumn.x, leftColumn.y, leftColumn.width, holdHeight);
        Rectangle controlsArea = new Rectangle(
                leftColumn.x,
                leftColumn.y + controlsTop,
                leftColumn.width,
                controlsHeight
        );
        Rectangle infoArea = new Rectangle(rightColumn.x, rightColumn.y, rightColumn.width, infoHeight);
        Rectangle queueArea = new Rectangle(
                rightColumn.x,
                rightColumn.y + queueTop,
                rightColumn.width,
                queueHeight
        );

        board.render(g, state, boardArea);
        hold.render(g, state, holdArea);
        controls.render(g, controlsArea, state);
        info.render(g, state, infoArea);
        queue.render(g, state, queueArea);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
