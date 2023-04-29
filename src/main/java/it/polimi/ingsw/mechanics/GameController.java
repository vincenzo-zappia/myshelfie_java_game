
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.Lobby;
import it.polimi.ingsw.network.messages.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectionMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GameController {

    //region ATTRIBUTES

    //attributi verso la parte del modello
    private final Game game;
    private final TurnManager turnManager;

    //attributi verso la parte di networking
    private HashMap<String, VirtualView> virtualViewMap;
    //endregion

    //region CONSTRUCTOR
    public GameController(ArrayList<String> playerUsernames, Game game, HashMap<String, VirtualView> virtualViewMap){
        turnManager = new TurnManager(playerUsernames);
        this.game = game;
        this.virtualViewMap = virtualViewMap;
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
     * @param message message sent by the client with the coordinates of the cards selected to be put
     *                into the player's Bookshelf
     */
    public void cardSelection(SelectionMessage message){
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board

            //Invio riscontro positivo al client
            VirtualView view = virtualViewMap.get(message.getUsername());
            view.sendResponse(true);

            //TODO: Invio broadcast aggiornamento Board (chi chiama sendBoardRefill())

        }

        //TODO: invio eccezione nel caso in cui le celle non sono selezionabili
    }

    //method that extracts the chosen column from the XML and inserts the cards previously selected into the player's bookshelf
    public void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            //inserzione carte nel Bookshelf di Player
            //TODO: gestire caso overflow carte selezionate (check legalità o eccezione metodo?)
            game.addCardToBookshelf(message.getUsername(), message.getSelectedColumn(), message.getSelectedCards());

            //Invio riscontro positivo al client
            VirtualView view = virtualViewMap.get(message.getUsername());
            view.sendResponse(true);

            endTurn();
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that performs end turn housekeeping routines: checking if a common goal was achieved,
     * checking if the player's Bookshelf got full (and if so starting the endgame) and checking if
     * the condition for the actual end of the game is reached.
     */
    private void endTurn(){
        game.scoreCommonGoal(turnManager.getCurrentPlayer());
        if(game.isPlayerBookshelfFull(turnManager.getCurrentPlayer())) turnManager.startEndGame();
        //qui avviene effettivamente il cambio turno (se è disponibile)
        if(!turnManager.nextTurn()) findWinner();
    }

    /**
     * Method that creates the final scoreboard, scoring the player's private goals.
     */
    public void findWinner(){
        game.scorePrivateGoal();

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
        //TODO: calling of Game method that creates ordered ArrayList of Players
    }

    //endregion

    //region GETTER AND SETTER
    //endregion
}
