package com.aquip.tetris;

import com.aquip.tetris.engine.GameEngine;
import com.aquip.tetris.input.InputFrame;
import com.aquip.tetris.input.InputSource;
import com.aquip.tetris.input.SwingInputSource;
import com.aquip.tetris.ui.game.GamePanel;
import com.aquip.tetris.ui.menu.MenuEngine;
import com.aquip.tetris.ui.menu.MenuInputMapper;
import com.aquip.tetris.ui.menu.MenuPanel;

import javax.swing.*;
import java.io.File;
import java.awt.event.KeyEvent;

public class Main {

    private final JFrame frame = new JFrame("Tetris");
    private final MenuPanel menuPanel = new MenuPanel();
    private final GamePanel gamePanel = new GamePanel();

    private InputSource inputSource;
    private MenuEngine menu;
    private GameEngine game;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().start());
    }

    private void start() {
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(menuPanel);
        frame.setVisible(true);
        requestFocus();

        inputSource = new SwingInputSource(frame.getRootPane());

        menu = new MenuEngine(
                new File("config/config.yml"),
                new MenuInputMapper()
        );
        menuPanel.setState(menu.state);

        Timer timer = new Timer(16, e -> tick());
        timer.setCoalesce(true);
        timer.start();
    }

    private void tick() {
        InputFrame inputFrame = inputSource.poll();

        if (game == null) {
            game = menu.update(inputFrame);

            menuPanel.setState(menu.state);
            menuPanel.repaint();

            if (game != null) {
                showGame();
            }
            return;
        }

        boolean gameOver = game.getMatchState().isGameOver();

        if (gameOver &&
                (inputFrame.pressed.contains(KeyEvent.VK_R)
                        || inputFrame.pressed.contains(KeyEvent.VK_ENTER))) {
            game = menu.createGame();
            showGame();
        } else if (inputFrame.pressed.contains(KeyEvent.VK_ESCAPE)) {
            game = null;
            menu.showPlayMenu();
            frame.setContentPane(menuPanel);
            frame.revalidate();
            frame.repaint();
            requestFocus();
        } else if (gameOver) {
            gamePanel.setState(game.getMatchState());
            gamePanel.repaint();
        } else {
            game.tick(inputFrame);
            gamePanel.setState(game.getMatchState());
            gamePanel.repaint();
        }
    }

    private void showGame() {
        gamePanel.setState(game.getMatchState());
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();
        requestFocus();
    }

    private void requestFocus() {
        frame.getRootPane().requestFocusInWindow();
    }
}
