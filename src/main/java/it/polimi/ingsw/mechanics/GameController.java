
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Player;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.client2server.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.client2server.SelectionMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Calls the VirtualView to sent messages to the Client. Receives messages from the Clients and defines the relative behavior of the Game.
 */
public class GameController {

    //region ATTRIBUTES

    //attributi verso la parte del modello
    private final Game game;
    private final TurnManager turnManager;

    //attributi verso la parte di networking
    private final HashMap<String, VirtualView> viewHashMap;
    //endregion

    //region CONSTRUCTOR
    public GameController(Game game, HashMap<String, VirtualView> viewHashMap){
        turnManager = new TurnManager(new ArrayList<>(viewHashMap.keySet()));
        this.game = game;
        this.viewHashMap = viewHashMap;
        broadcastMessage(MessageType.BOARD_REFILL);
    }
    //endregion

    //Every method is involved in the reception of a message, calling the specific action associated with the message and the generation of a response
    //region METHODS

    //TODO: Metodo inizio partita che dopo la creazione di Game e quindi di Board aggiorna i player del fillBoard() (forse in Lobby?)

    /**
     * Method that handles the received generic Message by checking its actual type and calls the wanted method
     * @param message received message
     */
    public synchronized void messageHandler(Message message){
        //Gestione logica turni
        if (!turnManager.getCurrentPlayer().equals(message.getUsername())) {
            System.out.println("INFO: player " + message.getUsername() + " non di turno");
            viewHashMap.get(message.getUsername()).sendNotYourTurn(); //Invio riscontro negativo al client
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
     * Send a broadcast message, used for refilling user's
     * boards or notify that a single removed certain cards
     * @param type of the message
     * @param payload eventual attributes of the received message
     */
    public void broadcastMessage(MessageType type, Object... payload){
        for(String username : viewHashMap.keySet()) {
            switch (type) {
                case BOARD_REFILL -> viewHashMap.get(username).showRefilledBoard(game.getBoard().getMatrix());
                case CARD_REMOVAL -> viewHashMap.get(username).showRemovedCards((int[][])payload[0]); //Primo oggetto che arriva castato a matrice
            }
        }
    }

    //TODO: Metodo startTurn() che chiama view.askCardSelection()

    //TODO: cardSelection() non invia più riscontro positivo ma chiama view.askCardInsertion()
    /**
     * method that extracts the coordinates from the message checking the validity of the selection
     * @param message message sent by the client with the coordinates of the cards selected to be put
     *                into the player's Bookshelf
     */
    public synchronized void cardSelection(SelectionMessage message){
        System.out.println("INFO: entrato in selection");
        if(game.isSelectable(message.getCoordinates())) {
            game.removeCardFromBoard(message.getCoordinates()); //Removal of the selected cards form the game board

            //Invio riscontro positivo al client (questo abilita lato client a effettuare inserzione)
            viewHashMap.get(message.getUsername()).sendResponse(true, MessageType.SELECTION_RESPONSE);

            //Invio a tutti i client la posizonie delle carete da rimuovere
            broadcastMessage(MessageType.CARD_REMOVAL, (Object) message.getCoordinates());
        }

        //Invio riscontro negativo al client
        else viewHashMap.get(message.getUsername()).sendResponse(false, MessageType.SELECTION_RESPONSE);
    }

    /**
     * Method that inserts the cards selected by the player into his bookshelf
     * @param message Message containing the cards arranged in the order picked by the player and the column into which
     *                he wants to put them in his bookshelf
     */
    public synchronized void cardInsertion(InsertionMessage message){
        //Insertion of the cards removed from the board into the player's bookshelf
        System.out.println("INFO: Entrato in insertion");
        try {

            //cards insertion in player's bookshelf
            //if statement can be simplified, not sure if it's correct with message's code

            if(game.addCardToBookshelf(message.getUsername(), message.getSelectedColumn(), message.getSelectedCards())){
                System.out.println("INFO: Carte inserite nella colonna " + message.getSelectedColumn());
                //Invio riscontro positivo al client
                viewHashMap.get(message.getUsername()).sendResponse(true, MessageType.INSERTION_RESPONSE);
            }
            else viewHashMap.get(message.getUsername()).sendResponse(false, MessageType.INSERTION_RESPONSE); //invia riscontro negativo


            endTurn();

        } catch (AddCardException e) {
            //Invio riscontro negativo al client
            viewHashMap.get(message.getUsername()).sendResponse(false, MessageType.INSERTION_RESPONSE);
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that performs end turn housekeeping routines: checking if a common goal was achieved,
     * checking if the player's Bookshelf got full (and if so starting the endgame) and checking if
     * the condition for the actual end of the game is reached.
     */
    private void endTurn(){
        //invio aggiornamento board a tutti i player nel caso in cui la board venga riempita
        if(game.checkRefill()) broadcastMessage(MessageType.BOARD_REFILL);

        //Check if the current player has achieved anyone of the common goals
        game.scoreCommonGoal(turnManager.getCurrentPlayer());

        //Check if the current player's bookshelf is full
        if(game.isPlayerBookshelfFull(turnManager.getCurrentPlayer())) turnManager.startEndGame();

        //Nella chiamata di nextTurn() avviene effettivamente il cambiamento del turno del giocatore (nel caso non sia l'ultimo)
        if(!turnManager.nextTurn()) findWinner();
    }

    /**
     * Method that creates the final scoreboard, scoring the player's private goals.
     */
    public void findWinner(){
        game.scorePrivateGoal();
        ArrayList<Player> scoreboard = game.orderByScore(); //estrarre giocatori partendo dal termine della lista

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players

    }
    //endregion

}
