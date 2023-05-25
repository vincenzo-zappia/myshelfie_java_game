package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.SerializableTreeMap;
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
        broadcastMessage(MessageType.CURRENT_PLAYER);
        broadcastMessage(MessageType.REFILLED_BOARD);
        broadcastMessage(MessageType.COMMON_GOAL);
        broadcastMessage(MessageType.PRIVATE_GOAL);

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
    public void messageHandler(Message message){

        //TODO: Per ora invio chat implementato in gamecontroller perché solo in scena Game (in clienthandler sarebbe stato peggio in quanto avrei dovuto filtrare tutti i messaggi)
        if (message.getType().equals(MessageType.CHAT)){
            for(String username : viewHashMap.keySet()) viewHashMap.get(username).showChat(message.getSender() + ": " + message.getContent());
            return;
        }

        //Checking if it's the turn of the player who sent the message
        if (!turnManager.getCurrentPlayer().equals(message.getSender())) {
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "It's not your turn!");
            System.out.println("INFO: Not " + message.getSender() + "'s turn.");
            return;
        }

        //Management of the received command by message type only if it's the player's turn
        switch (message.getType()){
            case SELECTION_REQUEST -> cardSelection((SelectionRequest) message);
            case INSERTION_REQUEST -> cardInsertion((InsertionRequest) message);
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
                case REFILLED_BOARD -> viewHashMap.get(username).showRefilledBoard(game.getBoard().getBoard());
                case REMOVED_CARDS -> viewHashMap.get(username).showRemovedCards((int[][])payload[0]); //Primo oggetto che arriva castato a matrice
                case CURRENT_PLAYER -> viewHashMap.get(username).showCurrentPlayer(turnManager.getCurrentPlayer());
                case COMMON_GOAL -> viewHashMap.get(username).showCommonGoals(game.getCommonGoals());
                case PRIVATE_GOAL -> viewHashMap.get(username).showPrivateGoal(game.getPlayer(username).getPrivateGoal());
                case TOKEN -> viewHashMap.get(username).showToken(turnManager.getCurrentPlayer());
                case SCOREBOARD -> viewHashMap.get(username).showScoreboard((SerializableTreeMap<String, Integer>) payload[0]);
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
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "Selection already made!");
            System.out.println("INFO: " + message.getSender() + " tried to make another selection");
            return;
        }

        //Saving the coordinates of the removed cards in order to broadcast them at the end of the turn
        coordinates = message.getCoordinates().clone(); //TODO: Sovrascrive: salva prima di controllare se selezione valida, serve per mantenere orine prima che canSelect lo sfasi con Arrays.sort

        //Checking if the cards selected are actually selectable
        if((message.getCoordinates().length != 0) && game.canSelect(message.getSender(), message.getCoordinates())) {

            //Sending positive feedback to the player with the checked coordinates
            viewHashMap.get(message.getSender()).sendCheckedCoordinates(true, message.getCoordinates());
            viewHashMap.get(message.getSender()).sendGenericResponse(true, "Valid selection!");
            System.out.println("INFO: " + message.getSender() + " made a valid selection");

            //Turn phase management: the player is now allowed to insert the selected cards into his bookshelf
            canInsert = true;
        }
        else{
            //Sending negative feedback to the player
            viewHashMap.get(message.getSender()).sendCheckedCoordinates(false, message.getCoordinates());
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "Invalid selection! Please retry.");
            System.out.println("INFO: " + message.getSender() + " made an invalid selection");
        }
    }

    /**
     * Calls the game command to insert the cards selected by the player into his bookshelf
     * @param message Message containing the cards arranged in the order picked by the player and the column into which
     *                he wants to put them in his bookshelf
     */
    public synchronized void cardInsertion(InsertionRequest message){

        //Checking if the player has first made a selection
        if(!canInsert){
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "First select your cards!" );
            System.out.println("INFO: " + message.getSender() + " tried to make an insertion before a selection");
            return;
        }

        //Checking if the column selected for the insertion is valid, if so the cards are inserted by the same method
        if(game.canInsert(turnManager.getCurrentPlayer(), message.getSelectedColumn(), coordinates.length)){

            //Removal of the previously selected cards from the game board
            ArrayList<Card> cards = game.removeSelectedCards(coordinates);

            //Insertion of the cards (updating the bookshelf of the player)
            game.addCardsToBookshelf(turnManager.getCurrentPlayer(), message.getSelectedColumn(), cards);
            System.out.println("INFO: Cards removed from the board and inserted in " + turnManager.getCurrentPlayer() + "'s bookshelf in column " + message.getSelectedColumn());

            //Sending positive feedback to the player with the updated bookshelf
            viewHashMap.get(message.getSender()).showUpdatedBookshelf(game.getPlayerBookshelf(turnManager.getCurrentPlayer()));
            viewHashMap.get(message.getSender()).sendGenericResponse(true, "Insertion successful!");

            //End turn housekeeping routine
            endTurn();
        }
        else {
            //Sending negative feedback to the player with the bookshelf without changes
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "Invalid column! Please select another." );
            System.out.println("INFO: " + message.getSender() + " made an invalid insertion");
        }

    }

    /**
     * Method that performs end turn housekeeping routines: checking if a common goal was achieved,
     * checking if the player's Bookshelf got full (and if so starting the endgame) and checking if
     * the condition for the actual end of the game is reached.
     */
    private void endTurn(){

        //Broadcasting to all the players the coordinates of the cards removed in the last turn
        broadcastMessage(MessageType.REMOVED_CARDS, (Object) coordinates);

        //Checking if the boards has to be refilled. If so, broadcasting the updated board to all the players
        if(game.checkRefill()){
            broadcastMessage(MessageType.REFILLED_BOARD);
            System.out.println("INFO: Board refilled.");
        }

        //Checking if the current player has achieved anyone of the common goals
        game.scoreCommonGoal(turnManager.getCurrentPlayer());
        broadcastMessage(MessageType.COMMON_GOAL);

        //Checking if the bookshelf of the current player got full
        if(game.isPlayerBookshelfFull(turnManager.getCurrentPlayer()) && !turnManager.inEndGame()){
            turnManager.startEndGame();
            System.out.println("INFO: Endgame started.");

            //Adding the bonus point to the first player who filled his bookshelf
            broadcastMessage(MessageType.TOKEN);
            game.getPlayer(turnManager.getCurrentPlayer()).addScore(1);
        }

        //Checking if the current player was the last one who had to play a turn, if so, starting the endgame, otherwise
        //calling for the next player
        if(!turnManager.nextTurn()) {
            findWinner();
        }
        else{
            //Broadcasting the username of the next player who plays a turn
            broadcastMessage(MessageType.CURRENT_PLAYER);

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
        SerializableTreeMap<String, Integer> scoreboard = game.orderByScore();

        System.out.println(scoreboard);

        //Broadcasting the scoreboard to all the players
        broadcastMessage(MessageType.SCOREBOARD, scoreboard);
    }
    //endregion

}
