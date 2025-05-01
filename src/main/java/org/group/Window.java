package org.group;

import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.Image;
import java.net.URL;

public class Window {
    private static int ROWS = 6;
    private static int COLS = 7;

    private final JFrame window;
    private final GameController controller;
    private final CellPanel[][] cells;

    private final JLabel statusLabel;
    private final JLabel winnerLabel;

    public Window(GameController controller) {
        this.controller = controller;

        ROWS = controller.getBoardHeight();
        COLS = controller.getBoardWidth();
        cells = new CellPanel[ROWS][COLS];

        window = new JFrame("Connect 4");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout(5,5));

        // --- Header + Buttons Panel ---
        JPanel northPanel = new JPanel(new BorderLayout());

        URL logoUrl = getClass().getClassLoader().getResource("org/group/Connect_4_game_logo.png");
        if (logoUrl == null) {
            System.err.println("Logo resource not found");
        }
        ImageIcon originalIcon = new ImageIcon(logoUrl);
        Image img = originalIcon.getImage();
        int newWidth = 300;
        int newHeight = img.getHeight(null) * newWidth / img.getWidth(null);
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);
        // Header panel to right-align logo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel header = new JLabel(logoIcon);
        header.setHorizontalAlignment(SwingConstants.RIGHT);
        headerPanel.add(header, BorderLayout.EAST);
        northPanel.add(headerPanel, BorderLayout.NORTH);

        // Buttons row
        JPanel top = new JPanel(new GridLayout(1, COLS, 2, 2));
        for (int c = 0; c < COLS; c++) {
            final int col = c;
            JButton btn = new JButton("↓");
            btn.setFont(btn.getFont().deriveFont(Font.BOLD, 14f));
            btn.addActionListener(e -> {
                controller.handleMove(col);
                updateBoard();
            });
            top.add(btn);
        }
        northPanel.add(top, BorderLayout.SOUTH);

        window.add(northPanel, BorderLayout.NORTH);

        // --- Game board ---
        JPanel board = new JPanel(new GridLayout(ROWS, COLS, 2,2));
        board.setBackground(Color.BLUE);
        
        System.out.println("ROWS: " + ROWS + "\nCOLS: " + COLS);
        
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
            updateBoard();
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

        window.setSize(400, 700);
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
        showMessage("Player " + player.getName() + " (" + player.getToken() + ")" + " won!");
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