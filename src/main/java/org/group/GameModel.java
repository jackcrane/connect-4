package org.group;
import java.io.Serializable;

public class GameModel implements Serializable{
    private GameBoard board;
    private Player[] players;
    private int currentPlayer;

    public GameModel(int boardWidth, int boardHeight, int playTo){
        board = new StandardBoard(boardWidth, boardHeight, playTo);
        players = new Player[]{
            new Player("Player 1", "Red"),
            new Player("Player 2", "Yellow")
        };
        currentPlayer = 0;
    }

    public boolean dropPiece(int col){
        if(board.isValidMove(col)){
            board.placePiece(new Piece(players[currentPlayer].getToken()), col);
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
    public Piece[][] getBoardState() {
        return board.getBoard();
    }
}
