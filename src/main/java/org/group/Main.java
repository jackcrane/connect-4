package org.group;

public class Main {
	
	int connectTo;
	int width;
	int height;
	
    public static void main(String[] args) {
    	
    	if (args.length != 0 && args.length != 3) {
    		System.out.println("Usage: java Main.java <Board Width> <Board Height> <Aligned Pieces to Win>");
    		System.exit(-1);
    	} else {
    		for (String word : args) {
    			try {
    				Integer.parseInt(word);
    			} catch (NumberFormatException e) {
    	    		System.out.println("Usage: java Main.java <Board Width> <Board Height> <Aligned Pieces to Win>");
    	    		System.exit(-1);
    			}
    		}
    	}
    	
    	if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1 || Integer.parseInt(args[2]) < 1) {
    		System.out.println("Usage: java Main.java <Board Width> <Board Height> <Aligned Pieces to Win>");
    		System.out.println("Board Width, Board Height, Aligned Pieces to Win cannot be less than 1.");
    		System.exit(-1);
    		
    	}
    	
    	if (args.length == 0) {
    		GameController gameController = new GameController(7, 6, 4);
    	} else if (args.length == 3) {
    		GameController gameController = new GameController(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    	}
        
    }
}