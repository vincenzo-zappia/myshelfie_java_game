
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.SelectionMessage;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {

    //region ATTRIBUTES

    //attributi verso la parte del modello
    private final Game game;
    private final TurnManager turnManager;

    //attributi verso la parte di networking
    private HashMap<String, VirtualView> virtualViewMap;
    private ArrayList<String> playerUsernames;
    //endregion

    //region CONSTRUCTOR
    public GameController(ArrayList<String> playerUsernames, Game game, HashMap<String, VirtualView> virtualViewMap){
        turnManager = new TurnManager(playerUsernames);
        this.game = game;
        this.virtualViewMap = virtualViewMap;
        this.playerUsernames = playerUsernames;
    }
    //endregion

    //region METHODS

    //TODO: Metodo inizio partita che dopo la creazione di Game e quindi di Board aggiorna i player del fillBoard() (forse in Lobby?)

    /**
     * Method that handles the received generic Message by checking its actual type and calls the wanted method
     * @param message received message
     */
    public synchronized void messageHandler(Message message){
        //Gestione logica turni
        if (!turnManager.getCurrentPlayer().equals(message.getUsername())) {

            //TODO: inviare messaggio di errore al player non di turno
            //Invio riscontro negativo al client
            virtualViewMap.get(message.getUsername()).sendResponse(false);
            return;
        }

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
    public synchronized void cardSelection(SelectionMessage message){
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board

            //Invio riscontro positivo al client
            virtualViewMap.get(message.getUsername()).sendResponse(true);

            //TODO: Invio broadcast aggiornamento Board (potrebbe essere implementato in Lobby)
            for(String username : playerUsernames){
                virtualViewMap.get(username).sendBoardRefill(game.getBoard());
            }
        }

        //TODO: invio eccezione nel caso in cui le celle non sono selezionabili (per ora messaggio negativo generale)
        //Invio riscontro negativo al client
        virtualViewMap.get(message.getUsername()).sendResponse(false);
    }

    /**
     * Method that inserts the cards selected by the player into his bookshelf
     * @param message Message containing the cards arranged in the order picked by the player and the column into which
     *                he wants to put them in his bookshelf
     */
    public synchronized void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        try {
            //inserzione carte nel Bookshelf di Player
            //TODO: gestire caso overflow carte selezionate (check legalità o eccezione metodo?)
            game.addCardToBookshelf(message.getUsername(), message.getSelectedColumn(), message.getSelectedCards());

            //Invio riscontro positivo al client
            virtualViewMap.get(message.getUsername()).sendResponse(true);

            endTurn();
        } catch (AddCardException e) {
            //Invio riscontro negativo al client
            virtualViewMap.get(message.getUsername()).sendResponse(false);
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

        //Nella chiamata di nextTurn() avviene effettivamente il cambiamento del turno del giocatore (nel caso non sia l'ultimo)
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
}
