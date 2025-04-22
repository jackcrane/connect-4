package org.group;
import java.io.Serializable;

public class GameModel implements Serializable{
    private GameBoard board;
    private Player[] players;
    private int currentPlayer;

    public GameModel(){
        board = new StandardBoard();
        players = new Player[]{
            new Player("Player 1", "Red"),
            new Player("Player 2", "Yellow")
        };
        currentPlayer = 0;
    }

    public boolean dropPiece(int col){
        if(board.isValidMove(col)){
            board.placePiece(new Piece(players[currentPlayer].toString()), col);
            return true;
        }
        return false;
    }

    public void switchPlayer(){
        currentPlayer = 1 - currentPlayer;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public boolean checkWin(){
        return board.checkWin() != null;
    }

    public boolean isDraw(){
        return board.isFull();
    }

    public void resetGame(){
        board.resetBoard();
        currentPlayer = 0;
    }
}
