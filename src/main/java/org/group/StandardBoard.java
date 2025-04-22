package org.group;

public class StandardBoard implements GameBoard {
	private Piece[][] board;
	private int width;
	private int height;
	
	public StandardBoard() {
		width = 7;
		height = 6;
		board = new Piece[width][height];
	}

	public StandardBoard(int width, int height) {
		this.width = width;
		this.height = height;
		board = new Piece[this.width][this.height];
	}

	public boolean isValidMove(int col) {
		return board[col][0] == null;
	}

	public boolean isFull() {
		return false;
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
	                for (int step = 1; step < 4; step++) {
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
	                for (int step = 1; step < 4; step++) {
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

	                if (count >= 4) {
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