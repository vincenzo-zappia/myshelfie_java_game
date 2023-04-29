
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectionMessage;

import java.util.ArrayList;

public class GameController {

    //region ATTRIBUTES
    private final Game game;
    private final TurnManager turnManager;
    //endregion

    //region CONSTRUCTOR
    public GameController(ArrayList<String> playerUsernames, Game game){
        turnManager = new TurnManager(playerUsernames);
        this.game = game;
    }
    //endregion

    //region METHODS

    //TODO: revisionare javaDoc
    /**
     *
     * @param message received message
     */
    public void messageHandler(Message message){
        //Gestione logica turni
        if (!turnManager.getCurrentPlayer().equals(message.getUsername())) return; //TODO: inviare messaggio di errore al player non di turno

        switch (message.getType()){
            case SELECTION_MESSAGE -> cardSelection((SelectionMessage) message);
            case INSERTION_MESSAGE -> cardInsertion((InsertionMessage) message);
            default -> {
                //TODO: generare eccezione?
            }
        }
    }

    /**
     * method that extracts the coordinates from the message checking the validity of the selection
     * @param message
     */
    public void cardSelection(SelectionMessage message){
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board
            //TODO: inviare messaggio di conferma
        }

        //TODO: invio eccezione nel caso in cui le celle non sono selezionabili
    }

    //method that extracts the chosen column from the XML and inserts the cards previously selected into the player's bookshelf
    public void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            game.addCardToBookshelf(message.getUsername(), message.getSelectedColumn(), message.getSelectedCards());
            endTurn(message.getUsername());
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }
    }

    private void endTurn(String username){
        game.scoreCommonGoal(turnManager.getCurrentPlayer());
        if(game.isPlayerBookshelfFull(username)) turnManager.startEndGame();
        if(!turnManager.nextTurn()) findWinner(); //qui avviene effettivamente il cambio turno (se è disponibile)
    }

    //method that creates the final scoreboard
    public void findWinner(){
        game.scorePrivateGoal();

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
        //TODO: calling of Game method that creates ordered ArrayList of Players
    }

    //endregion
}
