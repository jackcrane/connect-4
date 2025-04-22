package src;
import java.io.*;

public class GameController {
    private GameModel gameModel;
    private Window window;

    public GameController() {
        gameModel = new GameModel();
        window = new Window(this);
    }

    public void handleMove(int col){
        if(model.dropPiece(col)){
            if(model.checkWin()){
                window.showWinner(model.getCurrentPlayer());
            }
            else{
                model.switchPlayer();
                window.updateBoard();
            }
        }
        else{
            window.showMessage("Column is full. Try again.");
        }
    }

    public void restartGame(){
        model.resetGame();
        window.resetView();
    }

    public void saveGame(String filename){
        try{
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileout);
            objectOut.writeObject(model);
            objectOut.close();
            fileOut.close();
            System.out.println("Game saved to" + filename);
        } catch(IOException e){
            System.out.println("Error saving game: " + error.getMessage());
        }
    }

    public void loadGame(String filename){
        try{
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            model = (GameModel) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            window.updateBaord();
            System.out.println("Game loaded from" + filename);
        } catch(Exception error){
            System.out.println("Error loading game: " + error.getMessage());
        }
    }
}
