package src;
import java.io.Serializable;

public class GameModel implements Serializable{
    private Gameboard board;
    private Player[] players;
    private int currentPlayer;

    public GameModel(){
        board = new Gameboard();
        players = new Player[]{
            new Player("Player 1", "Red"),
            new Player("Player 2", "Yellow")
        };
        currentPlayer = 0;
    }

    public boolean dropPiece(int col){
        if(board.isValidMove(col)){
            board.placePiece(col, new Piece(players[currentPlayer].getColor()));
            return True;
        }
        return False;
    }

    public void switchPlayer(){
        currentPlayer = 1 - currentPlayer;
    }

    public Player getCurrentPlayer(){
        return players[currentPlayer];
    }

    public boolean checkWin(){
        return board.checkWin();
    }

    public boolean isDraw(){
        return board.isFull();
    }

    public void resetGame(){
        board.reset();
        currentPlayer = 0;
    }
}
