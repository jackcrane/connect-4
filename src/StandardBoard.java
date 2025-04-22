package src;

public class StandardBoard implements GameBoard {
	private Piece[][] board;
	private int width;
	private int height;
	
	public StandardBoard() {
		board = new Piece[7][6];
		height = 7;
		width = 6;
	}
	
	public StandardBoard(int width, int height) {
		board = new Piece[width][height];
		this.width = width;
		this.height = height;
		
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
	
}