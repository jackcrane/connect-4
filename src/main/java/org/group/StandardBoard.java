package org.group;

import java.io.Serializable;

public class StandardBoard implements GameBoard, Serializable {
	private Piece[][] board;
	private int width;
	private int height;
	private int STEPS;
	
	public StandardBoard() {
		width = 7;
		height = 6;
		STEPS = 4;
		board = new Piece[width][height];
	}

	public StandardBoard(int width, int height, int steps) {
		this.width = width;
		this.height = height;
		this.STEPS = steps;
		board = new Piece[this.width][this.height];
	}

	public boolean isValidMove(int col) {
		return board[col][0] == null;
	}

	public boolean isFull() {
		int fullCount = 0;
		for(int col = 0; col < width; col++) {
			if(board[col][0] != null) {
				fullCount++;
			}
		}
		return fullCount == width;
	}


	public boolean placePiece(Piece piece, int columnIndex) {
		for (int row = height - 1; row >= 0; row--) {
			if (checkSpot(columnIndex, row)) {
				board[columnIndex][row] = piece;
				return true;
			}
		}
		return false;
	}

	public Piece checkWin() {
	    // Directions: horizontal, vertical, and two diagonals
	    int[][] directions = {
	        {1, 0},  // horizontal
	        {0, 1},  // vertical
	        {1, 1},  // diagonal down-right
	        {1, -1}  // diagonal up-right
	    };

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            Piece current = board[x][y];
	            if (current == null) continue;

	            for (int[] dir : directions) {
	                int dx = dir[0], dy = dir[1];
	                int count = 1;

	                // Check in the positive direction
	                for (int step = 1; step < STEPS; step++) {
	                    int nx = x + dx * step;
	                    int ny = y + dy * step;
	                    if (nx < 0 || nx >= width || ny < 0 || ny >= height) break;
	                    Piece next = board[nx][ny];
	                    if (next != null && next.getColor().equals(current.getColor())) {
	                        count++;
	                    } else {
	                        break;
	                    }
	                }

	                // Check in the negative direction
	                for (int step = 1; step < STEPS; step++) {
	                    int nx = x - dx * step;
	                    int ny = y - dy * step;
	                    if (nx < 0 || nx >= width || ny < 0 || ny >= height) break;
	                    Piece next = board[nx][ny];
	                    if (next != null && next.getColor().equals(current.getColor())) {
	                        count++;
	                    } else {
	                        break;
	                    }
	                }

	                if (count >= STEPS) {
						System.out.println("WINNER" + " " + current);
	                    return current;
	                }
	            }
	        }
	    }

	    return null;
	}

	private boolean checkSpot(int columnIndex, int rowIndex) {
		if (board[columnIndex][rowIndex] == null) {
			return true;
		} else {
			return false;
		}
	}

	public void resetBoard() {
		for (int i = 0; i < height; i++) {
			for (int r = 0; r < width; r++) {
				board[r][i] = null;
			}
		}
	}

	public Piece[][] getBoard() {
		return board;
	}
}