package org.group;
import java.io.*;

public class GameController {
    private GameModel gameModel;
    private Window window;

    private int width;
    private int height;
    private int playTo;
    
    public GameController(int boardWidth, int boardHeight, int playTo) {
    	this.width = boardWidth;
    	this.height = boardHeight;
    	this.playTo = playTo;
    	
        gameModel = new GameModel(boardWidth, boardHeight, playTo);
        window = new Window(this);
    }

    public void handleMove(int col){
        if(gameModel.dropPiece(col)){
            if(gameModel.checkWin()){
                window.updateBoard();
                window.showWinner(gameModel.getCurrentPlayer());
            }
            if(gameModel.isDraw()) {
                window.updateBoard();
                window.showMessage("Draw. No one won");
            }
            else{
                gameModel.switchPlayer();
                window.updateBoard();
            }
        }
        else{
            window.showMessage("Column is full. Try again.");
        }
    }

    public void restartGame(){
        gameModel.resetGame();
        window.resetView();
    }

    public void saveGame(String filename){
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(gameModel);
            objectOut.close();
            fileOut.close();
            System.out.println("Game saved to" + filename);
        } catch(IOException error){
            System.out.println("Error saving game: " + error.getMessage());
        }
    }

    public void loadGame(String filename){
        try{
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            gameModel = (GameModel) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            window.updateBoard();
            System.out.println("Game loaded from" + filename);
        } catch(Exception error){
            System.out.println("Error loading game: " + error.getMessage());
        }
    }
    public Piece[][] getBoardState() {
        return gameModel.getBoardState();
    }

    public Player getCurrentPlayer() {
        return gameModel.getCurrentPlayer();
    }
    
    public int getBoardWidth() {
    	return width;
    }
    
    public int getBoardHeight() {
    	return height;
    }
    
    public int getPlayToNumber() {
    	return playTo;
    }
}
