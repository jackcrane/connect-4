package org.group;

import javax.swing.*;
import java.awt.*;

public class Window {
    private static final int ROWS = 6;
    private static final int COLS = 7;

    private final JFrame window;
    private final GameController controller;
    private final CellPanel[][] cells = new CellPanel[ROWS][COLS];

    private final JLabel statusLabel;
    private final JLabel winnerLabel;

    public Window(GameController controller) {
        this.controller = controller;

        window = new JFrame("Connect 4");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout(5,5));

        // --- Header ---
        String headerHtml =
                "<html><div style='text-align:center'>"
                        +   "<span style='font-size:12px;color:#444;'>THE ORIGINAL GAME OF</span><br>"
                        +   "<span style='font-size:36px;font-weight:bold;'>CONNECT 4</span>"
                        + "</div></html>";
        JLabel header = new JLabel(headerHtml, SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        window.add(header, BorderLayout.NORTH);

        // --- Drop buttons ---
        JPanel top = new JPanel(new GridLayout(1, COLS, 2,2));
        for (int c = 0; c < COLS; c++) {
            final int col = c;
            JButton btn = new JButton("Drop ↓");
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
            btn.addActionListener(e -> {
                controller.handleMove(col);
                updateBoard();
            });
            top.add(btn);
        }
        window.add(top, BorderLayout.BEFORE_FIRST_LINE);

        // --- Game board ---
        JPanel board = new JPanel(new GridLayout(ROWS, COLS, 2,2));
        board.setBackground(Color.BLUE);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c] = new CellPanel();
                board.add(cells[r][c]);
            }
        }
        window.add(board, BorderLayout.CENTER);

        // --- Bottom status & controls ---
        statusLabel = new JLabel();
        statusLabel.setOpaque(true);
        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setOpaque(true);
        winnerLabel.setBackground(Color.RED);
        winnerLabel.setForeground(Color.WHITE);
        winnerLabel.setFont(winnerLabel.getFont().deriveFont(Font.BOLD, 16f));
        winnerLabel.setVisible(false);
        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            String fn = JOptionPane.showInputDialog(window, "Save as:", "save.dat");
            if (fn != null) controller.saveGame(fn);
        });
        JButton loadBtn = new JButton("Load");
        loadBtn.addActionListener(e -> {
            String fn = JOptionPane.showInputDialog(window, "Load from:", "save.dat");
            if (fn != null) {
                controller.loadGame(fn);
                statusLabel.setText("Current Player: " + controller.getCurrentPlayer().getName());
                winnerLabel.setVisible(false);
            }
        });
        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            controller.restartGame();
            resetView();
            winnerLabel.setVisible(false);
            statusLabel.setText("Current Player: " + controller.getCurrentPlayer().getName());
        });

        JPanel left = new JPanel();
        left.add(statusLabel);
        left.add(saveBtn);
        left.add(loadBtn);
        left.add(resetBtn);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(left, BorderLayout.WEST);
        bottom.add(winnerLabel, BorderLayout.EAST);
        bottom.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        window.add(bottom, BorderLayout.SOUTH);

        window.setSize(800, 700);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        updateBoard();
    }

    public void updateBoard() {
        Piece[][] b = controller.getBoardState();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c].setPiece(b[c][r]);
            }
        }
        statusLabel.setText("Current Player: " + controller.getCurrentPlayer().getName());
        String currentToken = controller.getCurrentPlayer().getToken();
        if ("Red".equalsIgnoreCase(currentToken)) {
            statusLabel.setBackground(Color.RED);
            statusLabel.setForeground(Color.WHITE);
        } else if ("Yellow".equalsIgnoreCase(currentToken)) {
            statusLabel.setBackground(Color.YELLOW);
            statusLabel.setForeground(Color.BLACK);
        } else {
            statusLabel.setBackground(null);
            statusLabel.setForeground(Color.BLACK);
        }
        window.repaint();
    }

    public void showWinner(Player player) {
        winnerLabel.setText("Player " + player.getName() + " won!");
        winnerLabel.setVisible(true);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(window,
                message,
                "Notice",
                JOptionPane.WARNING_MESSAGE);
    }

    public void resetView() {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                cells[r][c].setPiece(null);
        window.repaint();
    }

    // A single nested class for board cells — no extra files needed
    private static class CellPanel extends JPanel {
        private Piece piece;

        void setPiece(Piece p) {
            piece = p;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Board background
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
            // Disc if present
            if (piece != null) {
                g.setColor("Red".equals(piece.getColor()) ? Color.RED : Color.YELLOW);
                int d = Math.min(getWidth(), getHeight()) - 10;
                g.fillOval((getWidth() - d)/2,
                        (getHeight() - d)/2,
                        d, d);
            } else {
                // empty slot
                g.setColor(Color.LIGHT_GRAY);
                int d = Math.min(getWidth(), getHeight()) - 10;
                g.fillOval((getWidth() - d)/2,
                        (getHeight() - d)/2,
                        d, d);
            }
        }
    }
}