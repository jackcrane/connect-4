package org.group;

import javax.swing.JFrame;

public class Window {
    private JFrame window;
    private GameController controller;

    public Window(GameController controller) {
        this.controller = controller;
        window = new JFrame();
    }

    public void showWinner(Player player) {
        System.out.println(player);
    }

    public void updateBoard() {}

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void resetView() {}
}