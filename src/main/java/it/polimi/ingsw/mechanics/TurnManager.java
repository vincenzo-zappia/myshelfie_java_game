package it.polimi.ingsw.mechanics;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * "Iterator" that has the role to select the current player relatively to the logic of the game
 */
public class TurnManager {

    //region ATTRIBUTES
    private final ArrayList<String> playerUsernames;
    private String currentPlayer;
    private boolean endGame;
    //endregion

    //region CONSTRUCTOR
    public TurnManager(ArrayList<String> playerUsernames){
        this.playerUsernames = playerUsernames;
        this.currentPlayer = playerUsernames.get(0);
        System.out.println("INFO: Current player is: " + currentPlayer);
        endGame = false;

        //Printing the order in which the players will make their moves
        System.out.print("INFO: Turn order: ");
        System.out.println("\t" + String.join(" -> ", playerUsernames));
    }
    //endregion

    //region METHODS
    /**
     * Sets the current player according to the rules of the game. It circularly iterates
     * through the players. If the endgame phase has started it stops at the last username which corresponds
     * to the player to the right of the sofa.
     */
    public boolean nextTurn(){
        int currentPlayerIndex = playerUsernames.indexOf(currentPlayer);

        //Checking if an iteration has ended, if so, and the endgame has not yet started, it starts back from the couch
        if(currentPlayerIndex + 1 < playerUsernames.size()) currentPlayerIndex += 1;
        else if(!endGame) currentPlayerIndex = 0;
        else return false;
        currentPlayer = playerUsernames.get(currentPlayerIndex);
        System.out.println("INFO: Current player is: " + currentPlayer);
        return true;
    }

    /**
     * Starts the endGame: the iteration of the player is set to stop to the last username which
     * corresponds to the player to the right of the couch.
     */
    public void startEndGame(){
        endGame = true;
    }

    public boolean inEndGame(){
        return endGame;
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }
    //endregion

}
