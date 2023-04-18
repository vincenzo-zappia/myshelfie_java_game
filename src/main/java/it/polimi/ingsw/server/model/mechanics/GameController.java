
package it.polimi.ingsw.server.model.mechanics;

import it.polimi.ingsw.server.ServerSideController;
import it.polimi.ingsw.exceptions.AddCardException;
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
    public GameController(ArrayList<String> playerUsernames, Game game, ServerSideController serverSideController){
        this.playerUsernames = playerUsernames;
        currentPlayer = playerUsernames.get(0); //TODO: the couch
        this.game = game;
        endGame = false;

    }
    //endregion

    //region METHODS
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
    public void turnAction(){
        //int playerIndex = playerUsernames.indexOf(currentPlayer);

        //TODO: da dove viene il file XML?
        //TODO: chiamata metodo in attesa del comando (forse metodo di Lobby)
        //temporary example
        String fileXML = "Carte";
        cardInsertion(fileXML, cardSelection(fileXML));

        //if the bookshelf is full then the endgame begins
        if(game.isPlayerBookshelfFull(currentPlayer)) endGame = true;

        //If the game isn't ended then the current player changes and the action of the turn is called again recursively
        nextTurn();
        turnAction();
    }

    //method that extracts the coordinates from the XML command, checks the validity of the selection and turns the
    //coordinates into their corresponding Cards
    public ArrayList<Card> cardSelection(String xmlSelection){
        //TODO: estrazione coordinate da XML
        //temporary example
        int[][] coordinates = new int[][]{{2, 3}};

        if(game.isSelectable(coordinates)) {
            return game.removeCardFromBoard(coordinates); //Removal of the selected cards form the game board
        }

        //TODO: brutto
        else return null;
    }

    //method that extracts the chosen column from the XML and inserts the cards previously selected into the player's bookshelf
    public void cardInsertion(String xmlInsertion, ArrayList<Card> selectedCards){
        //TODO: estrazione colonna XML
        //temporary example
        int selectedColumn = 0;

        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            game.addCardToBookshelf(currentPlayer, selectedColumn, selectedCards);
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
