package it.polimi.ingsw.mechanics;

import java.util.ArrayList;

//TODO: Decidere se il TurnManager debba essere in grado di inviare messaggi ai client. In quel caso diventerebbe "TurnController" e avrebbe bisogno di un'hashmap come attributo.
/**
 * "Iterator" that has the role to select the current player relatively to the logic of the game
 */
public class TurnManager {

    //region ATTRIBUTES
    private final ArrayList<String> playerUsernames;
    private String currentPlayer;
    private boolean endGame;
    //private turnPhase currentPhase;
    //endregion

    //enum turnPhase {START, SELECTION, INSERTION, END};

    //region CONSTRUCTOR
    public TurnManager(ArrayList<String> playerUsernames){
        this.playerUsernames = playerUsernames;
        endGame = false;
    }
    //endregion

    //region METHODS

    /**
     * Method that sets the current player according to the rules of the game. It circularly iterates
     * through the players. If the endgame phase started it stops at the last username which corresponds
     * to the player to the right of the sofa.
     */
    public boolean nextTurn(){
        int currentPlayerIndex = playerUsernames.indexOf(currentPlayer);

        //circular iteration of turns
        if(currentPlayerIndex + 1 < playerUsernames.size()) currentPlayerIndex += 1;
        else if(!endGame) currentPlayerIndex = 0;
        else return false;
        currentPlayer = playerUsernames.get(currentPlayerIndex);
        return true;
    }

    /**
     * Method that starts the endGame: the iteration of the player is set to stop to the last username which
     * corresponds to the player to the right of the sofa.
     */
    public void startEndGame(){
        endGame = true;
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }

    //endregion

}
