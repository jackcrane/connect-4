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
		return true;
	}

	public boolean isFull() {
		return false;
	}


	public boolean placePiece(Piece piece, int columnIndex) {
		for (int i = 0; i < height; i++) {
			if (checkSpot(columnIndex,i)) {
				board[columnIndex][i] = piece;
				return true;
			}
		}

		return false;
	}

	public Piece checkWin(Piece checkedPiece) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				if (board[j][i].equals(checkedPiece)) {

					//Check for matches all around
					for (int row = -1; row <=1; row++) {
						for (int col = -1; col <= 1; col++) {

							// Position does not exist, so just continue on.
							if ((i+row < 0 || i+row >= height) || (j+col < 0 || j+col >= width)) {
								continue;
							}
							else {
								byte tick = 1;
								byte xView = (byte)(i+row+row);
								byte yView = (byte)(j+col+col);

								//Check in a line.
								while (true) { // If the called position doesn't exist, break.
									if ((yView < 0 || yView >= height) || (xView < 0 || xView >= width)) {
										break;
									}

									if (board[xView][yView].equals(checkedPiece)) {
										tick++;
										xView += row;
										yView += col;

										if (tick >= 4) { // If we get to four in a row, someone won!
											return checkedPiece;
										}
									} else { // If we don't have four in a row, break.
										break;
									}
								}
							}
						}
					}
				}
			}
		}

		// No piece found.
		return null;
	}

	public Piece checkWin() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Piece current = board[j][i];
				if (current == null) continue;

				for (int row = -1; row <= 1; row++) {
					for (int col = -1; col <= 1; col++) {
						if (row == 0 && col == 0) continue;

						int xView = j + col;
						int yView = i + row;
						int tick = 1;

						while (xView >= 0 && xView < width && yView >= 0 && yView < height
								&& board[xView][yView] != null
								&& board[xView][yView].equals(current)) {
							tick++;
							if (tick >= 4) return current;

							xView += col;
							yView += row;
						}
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