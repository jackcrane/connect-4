package org.group;

import java.io.Serializable;

public interface GameBoard extends Serializable {
	boolean placePiece(Piece piece, int columnIndex);
//	Piece checkWin(Piece checkedPiece);
	Piece checkWin();
	void resetBoard();
	boolean isValidMove(int columnIndex);
	boolean isFull();
	Piece[][] getBoard();
}