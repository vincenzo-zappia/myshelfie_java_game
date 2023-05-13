
package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.network.messages.client2server.InsertionRequest;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.client2server.SelectionRequest;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Calls the VirtualView to sent messages to the Client. Receives messages from the Clients and defines the relative behavior of the Game.
 */
public class GameController {

    //region NETWORK ATTRIBUTES
    //attributi verso la parte del modello
    private final Game game;
    private final TurnManager turnManager;

    //attributi verso la parte di networking
    private final HashMap<String, VirtualView> viewHashMap;
    //endregion

    //TODO: Vedere se si riesce a renderli locali
    //region LOGIC ATTRIBUTES
    private int[][] coordinates; //Turn-specific board coordinates temporarily saved as attributes to be sent as message
    private boolean canInsert; //Turn phase management
    //endregion

    //region CONSTRUCTOR
    public GameController(Game game, HashMap<String, VirtualView> viewHashMap){
        turnManager = new TurnManager(new ArrayList<>(viewHashMap.keySet()));
        this.game = game;
        this.viewHashMap = viewHashMap;

        //Sending to each player initial game details: first player to make a move, board status, the game common
        //goals and their specific private goal
        broadcastMessage(MessageType.CURRENT_PLAYER_UPDATE);
        broadcastMessage(MessageType.BOARD_REFILL);
        broadcastMessage(MessageType.GOALS_DETAILS);

        //The game starts in the selection phase
        canInsert = false;
    }
    //endregion

    //Each method receives a message, calls for the specific action requested by the message and generates a response
    //region METHODS
    /**
     * Method that handles the received generic Message by checking its actual type and calls the wanted method
     * @param message received message
     */
    public synchronized void messageHandler(Message message){

        //Checking if it's the turn of the player who sent the message
        if (!turnManager.getCurrentPlayer().equals(message.getUsername())) {
            viewHashMap.get(message.getUsername()).sendGenericResponse(false, "It's not your turn!");
            System.out.println("INFO: Not " + message.getUsername() + "'s turn.");
            return;
        }

        //Management of the received command by message type only if it's the player's turn
        switch (message.getType()){
            case SELECTION_REQUEST -> cardSelection((SelectionRequest) message);
            case INSERTION_REQUEST -> cardInsertion((InsertionRequest) message);
            default -> {
                //TODO: generare eccezione?
            }
        }
    }

    /**
     * Sends the same message to all the players
     * @param type of the message
     * @param payload eventual attributes of the received message
     */
    public void broadcastMessage(MessageType type, Object... payload){
        for(String username : viewHashMap.keySet()) {
            switch (type) {
                case BOARD_REFILL -> viewHashMap.get(username).showRefilledBoard(game.getBoard().getMatrix());
                case CARDS_REMOVE_UPDATE -> viewHashMap.get(username).showRemovedCards((int[][])payload[0]); //Primo oggetto che arriva castato a matrice
                case CURRENT_PLAYER_UPDATE -> viewHashMap.get(username).showCurrentPlayer(turnManager.getCurrentPlayer());
                case SCOREBOARD -> viewHashMap.get(username).showScoreboard((HashMap<String, Integer>) payload[0]);
                case GOALS_DETAILS -> viewHashMap.get(username).sendGoals(game.getCommonGoals(), game.getPlayer(username).getPrivateGoal());
            }
        }
    }

    /**
     * Extracts the coordinates of the selected cards from the message and checks its validity (does not call the method
     * to remove them from the board)
     * @param message message sent by the client with the coordinates of the selected cards
     */
    public synchronized void cardSelection(SelectionRequest message){

        //Checking if the player has already made a selection
        if(canInsert){
            viewHashMap.get(message.getUsername()).sendGenericResponse(false, "Selection already made!");
            System.out.println("The player has already made his selection.");
            return;
        }

        //Checking if the cards selected are actually selectable
        if(game.canSelect(message.getCoordinates())) {

            //TODO: Via-vai di coordinate, se si vuole confinare l'aggiornamento delle coordinate di VirtualModel solo al lato client trovare alternativa
            //Sending positive feedback to the player with the checked coordinates
            viewHashMap.get(message.getUsername()).sendGenericResponse(true, "Valid selection!");
            viewHashMap.get(message.getUsername()).sendCheckedCoordinates(message.getCoordinates());
            System.out.println("INFO: Selection made.");

            //Saving the coordinates of the removed cards in order to broadcast them at the end of the turn
            coordinates = message.getCoordinates();

            //Turn phase management: the player is now allowed to insert the selected cards into his bookshelf
            canInsert = true;
        }

        //TODO: Debuggare: non arriva mai a questo else nel caso in cui la selezione non sia legale
        //Sending negative feedback to the player
        else{
            viewHashMap.get(message.getUsername()).sendGenericResponse(false, "Invalid selection! Please retry.");
            System.out.println("INFO: Selection not made.");
        }
    }

    /**
     * Calls the game command to insert the cards selected by the player into his bookshelf
     * @param message Message containing the cards arranged in the order picked by the player and the column into which
     *                he wants to put them in his bookshelf
     */
    public synchronized void cardInsertion(InsertionRequest message){
        try {

            //TODO: Non dovrebbe mai arrivarci perché c'è boolean lato client, rimuovere quello in cli?
            //Checking if the player has first made a selection
            if(!canInsert){
                viewHashMap.get(message.getUsername()).sendGenericResponse(false, "First select your cards!" );
                System.out.println("INFO: The player has to make the selection before the insertion.");
                return;
            }

            //Checking if the column selected for the insertion is valid, if so the cards are inserted by the same method
            if(game.addCardsToBookshelf(turnManager.getCurrentPlayer(), message.getSelectedColumn(), message.getSelectedCards())){

                //Removal of the previously selected cards from the game board
                game.removeCardsFromBoard(coordinates);
                System.out.println("INFO: Cards removed.");
                System.out.println("INFO: Cards inserted in column " + message.getSelectedColumn());

                //Sending positive feedback to the player with the updated bookshelf
                viewHashMap.get(message.getUsername()).sendGenericResponse(true, "Insertion successful!" );
                viewHashMap.get(message.getUsername()).sendUpdatedBookshelf(game.getPlayerBookshelf(turnManager.getCurrentPlayer())); //TODO: Debug: Una volta mi è capitato che non andasse oltre questo comando
                System.out.println("INFO: Inserted cards "+ game.getPlayerBookshelf(turnManager.getCurrentPlayer())[5][0].getCard().getType().toString());

                //End turn housekeeping routine
                endTurn();
            }

            //Sending negative feedback to the player with the bookshelf without changes
            else {
                viewHashMap.get(message.getUsername()).sendGenericResponse(false, "Invalid column! Please select another." );
                System.out.println("INFO: Cards not inserted.");
            }

        } catch (AddCardException e) {
            //Invio riscontro negativo al client
            viewHashMap.get(message.getUsername()).sendGenericResponse(false, "Boh, qualcosa sull'inserzione."); //TODO: Specificare tipo di problema
            throw new RuntimeException(e);
        }
    }

    //TODO: è possibile che le stesse coordinate vengano inviate due volte, gestire
    /**
     * Method that performs end turn housekeeping routines: checking if a common goal was achieved,
     * checking if the player's Bookshelf got full (and if so starting the endgame) and checking if
     * the condition for the actual end of the game is reached.
     */
    private void endTurn(){

        //Broadcasting to all the players the coordinates of the cards removed in the last turn
        broadcastMessage(MessageType.CARDS_REMOVE_UPDATE, (Object) coordinates);

        //Checking if the boards has to be refilled. If so, broadcasting the updated board to all the players
        if(game.checkRefill()){
            broadcastMessage(MessageType.BOARD_REFILL);
            System.out.println("INFO: Board refilled.");
        }

        //Checking if the current player has achieved anyone of the common goals
        game.scoreCommonGoal(turnManager.getCurrentPlayer());

        //Checking if the bookshelf of the current player got full
        if(game.isPlayerBookshelfFull(turnManager.getCurrentPlayer())){
            turnManager.startEndGame();
            System.out.println("INFO: Endgame started.");
        }

        //Checking if the current player was the last one who had to play a turn, if so, starting the endgame, otherwise
        //calling for the next player
        if(!turnManager.nextTurn()) findWinner();
        else{
            //Broadcasting the username of the next player who plays a turn
            broadcastMessage(MessageType.CURRENT_PLAYER_UPDATE);

            //TODO: Rilevante a fine gioco?
            //Turn phase management
            canInsert = false;
        }
    }

    /**
     * Scores the private goal for every player and creates the final scoreboard announcing the winner
     */
    public void findWinner(){
        //Scoring each individual private goal
        game.scorePrivateGoal();

        //Creating the scoreboard (sort algorithm in client)
        HashMap<String, Integer> scoreboard = game.getScoreboard();


        //Broadcasting the scoreboard to all the players
        broadcastMessage(MessageType.SCOREBOARD, (Object) scoreboard);
    }
    //endregion

}
