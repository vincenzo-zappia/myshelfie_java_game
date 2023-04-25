
package it.polimi.ingsw.server.model.mechanics;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.server.messages.InsertionMessage;
import it.polimi.ingsw.server.messages.SelectionMessage;
import it.polimi.ingsw.server.model.state.Game;
import it.polimi.ingsw.server.model.entities.Card;

import java.util.ArrayList;

public class GameController {

    //region ATTRIBUTES
    private final Game game;
    private final ArrayList<String> playerUsernames;
    private String currentPlayer;
    private boolean endGame;
    //endregion

    //region CONSTRUCTOR
    public GameController(ArrayList<String> playerUsernames, Game game, ClientHandler clientHandler){
        this.playerUsernames = playerUsernames;
        currentPlayer = playerUsernames.get(0); //TODO: the couch
        this.game = game;
        endGame = false;

    }
    //endregion

    //region TURN
    //TODO: Esportarlo in una classe esterna?
    //method that sets the current player and starts the endgame
    public void nextTurn(){
        int currentPlayerIndex = playerUsernames.indexOf(currentPlayer);

        //circular iteration of turns
        if(currentPlayerIndex + 1 < playerUsernames.size()) currentPlayerIndex += 1;
        else if(!endGame) currentPlayerIndex = 0;
            //the player to the right of the sofa is the last player (the player who goes after the first one is the one to his left because the game turns go clockwise)
        else {
            findWinner();
            return;
        }
        currentPlayer = playerUsernames.get(currentPlayerIndex);
    }

    //method that checks the validity of the player's move and calls the game methods
    //TODO: review required
    //TODO: dividere ancor di più le fasi quando sarà da implementarsi la GUI (routine di inizio e fine turno)
    public void startTurn(){
        //int playerIndex = playerUsernames.indexOf(currentPlayer);

        //TODO: chiamata metodo in attesa del comando (forse metodo di Lobby)

        //TODO: CHI PASSA PARAMETRI??????
        //cardSelection();
        //cardInsertion();

        //if the bookshelf is full then the endgame begins
        if(game.isPlayerBookshelfFull(currentPlayer)) endGame = true;

        //If the game isn't ended then the current player changes and the action of the turn is called again recursively
        nextTurn();
        startTurn();
    }
    //endregion

    //region METHODS

    //TODO: Metodo che chiama il primo startTurn()
    public void startGame(){
        //TODO: Inizializzazione

        startTurn();
    }


    //method that extracts the coordinates from the XML command, checks the validity of the selection and turns the
    //coordinates into their corresponding Cards
    public void cardSelection(SelectionMessage message){
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board
        }

        //TODO: notifica gli altri player (chiamata metodi VirtualView) (Observer)

        //TODO: invio eccezione
    }

    //method that extracts the chosen column from the XML and inserts the cards previously selected into the player's bookshelf
    public void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            game.addCardToBookshelf(currentPlayer, message.getSelectedColumn(), message.getSelectedCards());
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }
    }

    //method that creates the final scoreboard
    public void findWinner(){
        game.scoreCommonGoal();
        game.scorePrivateGoal();

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
        //TODO: calling of Game method that creates ordered ArrayList of Players
    }
    //endregion
}
