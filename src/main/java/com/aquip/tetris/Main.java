package com.aquip.tetris;

import com.aquip.tetris.engine.GameEngine;
import com.aquip.tetris.input.InputFrame;
import com.aquip.tetris.input.InputSource;
import com.aquip.tetris.input.SwingInputSource;
import com.aquip.tetris.menu.*;
import com.aquip.tetris.renderer.GamePanel;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Tetris");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuPanel menuPanel = new MenuPanel();
        GamePanel gamePanel = new GamePanel();

        frame.setContentPane(menuPanel);
        frame.setVisible(true);

        // ONE input source
        InputSource inputSource = new SwingInputSource(frame.getRootPane());

        MenuEngine menu = new MenuEngine(
                new File("config/config.yml"),
                new File("config/input.yml"),
                new MenuInputMapper()
        );

        GameEngine game = null;

        while (true) {

            InputFrame inputFrame = inputSource.poll();

            if (game == null) {

                game = menu.update(inputFrame);

                menuPanel.setState(menu.state);
                menuPanel.repaint();

                if (game != null) {
                    frame.setContentPane(gamePanel);
                    frame.revalidate();
                    frame.repaint();
                }

            } else {

                game.tick(inputFrame);

                gamePanel.setState(game.getMatchState());
                gamePanel.repaint();
            }

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}