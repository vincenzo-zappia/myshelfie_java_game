
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.InsertionMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.SelectionMessage;

import java.util.ArrayList;
import java.util.HashMap;

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
            viewHashMap.get(message.getUsername()).sendNotYourTurn();
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

            //Invio riscontro positivo al client (questo abilita lato client a effettuare inserzione)
            viewHashMap.get(message.getUsername()).sendSelectionResponse(true);

            //Invio a tutti i client la posizonie delle carete da rimuovere
            broadcastMessage(MessageType.CARD_REMOVAL, (Object) message.getCoordinates());
        }

        //Invio riscontro negativo al client
        viewHashMap.get(message.getUsername()).sendSelectionResponse(false);
    }

    public void broadcastMessage(MessageType type, Object... payload){
        for(String username : viewHashMap.keySet()) {
            switch (type) {
                case BOARD_REFILL -> viewHashMap.get(username).sendBoardRefill(game.getBoard());
                case CARD_REMOVAL -> viewHashMap.get(username).sendCardRemoval((int[][])payload[0]); //Primo oggetto che arriva castato a matrice
            }
        }
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
            viewHashMap.get(message.getUsername()).sendInsertionResponse(true);

            endTurn();
        } catch (AddCardException e) {
            //Invio riscontro negativo al client
            viewHashMap.get(message.getUsername()).sendInsertionResponse(false);
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

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
        //TODO: calling of Game method that creates ordered ArrayList of Players
    }

    //endregion

}
