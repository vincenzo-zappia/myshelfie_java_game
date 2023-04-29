package it.polimi.ingsw.mechanics;

import java.util.ArrayList;

public class TurnManager {

    //region ATTRIBUTES
    private final Game game; //TODO: Decidere se accesso diretto o indiretto
    private final ArrayList<String> playerUsernames;
    private String currentPlayer;
    private boolean endGame;
    private turnPhase currentPhase;
    //endregion

    enum turnPhase {START, SELECTION, INSERTION, END};

    //region CONSTRUCTOR
    public TurnManager(ArrayList<String> playerUsernames, Game game){
        this.playerUsernames = playerUsernames;
        this.game = game; //TODO: rimuovere o meno in base a decisione

    }
    //endregion

    //region METHODS

    /**
     * method that calls the first startTurn() and sets the initial phase(?)
     */
    //TODO: (forse gestito da Lobby)
    public void startGame(){
        //TODO: Inizializzazione (fase?)

        startTurn();
    }

    /**
     * method that sets the current player and starts the endgame
     */
    public void nextTurn(){
        int currentPlayerIndex = playerUsernames.indexOf(currentPlayer);

        //circular iteration of turns
        if(currentPlayerIndex + 1 < playerUsernames.size()) currentPlayerIndex += 1;
        else if(!endGame) currentPlayerIndex = 0;
            //the player to the right of the sofa is the last player (the player who goes after the first one is the one to his left because the game turns go clockwise)
        else {
            //TODO: spostare implementazione logica di gioco: in base alla fase corrente setatta da TurnController
            //TODO: Lobby chiamerà gli eventi di gioco
            //findWinner();
            return;
        }
        currentPlayer = playerUsernames.get(currentPlayerIndex);
    }

    /**
     * method that checks the validity of the player's move and calls the game methods
     */
    //TODO: review required
    //TODO: dividere ancor di più le fasi quando sarà da implementarsi la GUI (routine di inizio e fine turno)
    public void startTurn(){
        //int playerIndex = playerUsernames.indexOf(currentPlayer);

        //TODO: Check legalità verrà fatto in Lobby(?) e non nello gestore turni
        //if the bookshelf is full then the endgame begins
        if(game.isPlayerBookshelfFull(currentPlayer)) endGame = true;

        //If the game isn't ended then the current player changes and the action of the turn is called again recursively
        nextTurn();
        startTurn();
    }

    //endregion

}
