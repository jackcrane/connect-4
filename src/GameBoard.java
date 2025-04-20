package src;

import java.io.Serializable;

public interface GameBoard extends Serializable {
	boolean placePiece(Piece piece, int columnIndex);
	Piece checkWin(Piece checkedPiece);
	void resetBoard();
	
}