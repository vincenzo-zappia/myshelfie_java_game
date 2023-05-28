package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.Scoreboard;
import it.polimi.ingsw.network.Lobby;
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
    private final Lobby lobby;
    private final HashMap<String, VirtualView> viewHashMap;
    //endregion

    //region GAME ATTRIBUTES
    private final Game game;
    private final TurnManager turnManager;
    private int[][] coordinates; //Turn-specific board coordinates temporarily saved as attributes to be sent as message
    private boolean canInsert; //Turn phase management
    //endregion

    //region CONSTRUCTOR
    public GameController(Lobby lobby, Game game, HashMap<String, VirtualView> viewHashMap){
        turnManager = new TurnManager(new ArrayList<>(viewHashMap.keySet()));
        this.lobby = lobby;
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

        //Checking if it's the turn of the player who sent the message
        if (!turnManager.getCurrentPlayer().equals(message.getSender())) {
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "It's not your turn!");
            System.out.println("INFO: Not " + message.getSender() + "'s turn");
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
     * @param payload eventual attributes of the message to send
     */
    public void broadcastMessage(MessageType type, Object... payload){
        for(String username : viewHashMap.keySet()) {
            switch (type) {
                case REFILLED_BOARD -> viewHashMap.get(username).showRefilledBoard(game.getBoard().getBoard());
                case REMOVED_CARDS -> viewHashMap.get(username).showRemovedCards((int[][])payload[0]); //Casting the first object of the payload to an integer matrix
                case CURRENT_PLAYER -> viewHashMap.get(username).showCurrentPlayer(turnManager.getCurrentPlayer());
                case COMMON_GOAL -> viewHashMap.get(username).showCommonGoals(game.getCommonGoals());
                case PRIVATE_GOAL -> viewHashMap.get(username).showPrivateGoal(game.getPlayer(username).getPrivateGoal());
                case TOKEN -> viewHashMap.get(username).showToken(turnManager.getCurrentPlayer());
                case SCOREBOARD -> viewHashMap.get(username).showScoreboard((Scoreboard) payload[0]);
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
        coordinates = message.getCoordinates().clone();

        //Checking if the cards selected are actually selectable
        if((message.getCoordinates().length != 0) && game.canSelect(message.getSender(), message.getCoordinates())) {

            //Sending positive feedback to the player with the checked coordinates
            viewHashMap.get(message.getSender()).sendCheckedCoordinates(true, message.getCoordinates());
            viewHashMap.get(message.getSender()).sendGenericResponse(true, "Valid selection! Insert your cards!");
            System.out.println("INFO: " + message.getSender() + " made a valid selection");

            //Turn phase management: the player is now allowed to insert the selected cards into their bookshelf
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

        //Checking if the player didn't make a selection
        if(!canInsert){
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "First select your cards!" );
            System.out.println("INFO: " + message.getSender() + " tried to make an insertion before a selection");
            return;
        }

        //Checking if the column selected for the insertion is valid
        if(game.canInsert(turnManager.getCurrentPlayer(), message.getSelectedColumn(), coordinates.length)){

            //Removing the previously selected cards from the game board
            ArrayList<Card> cards = game.removeSelectedCards(coordinates);

            //Inserting the cards (updating the bookshelf of the player)
            game.addCardsToBookshelf(turnManager.getCurrentPlayer(), message.getSelectedColumn(), cards);
            System.out.println("INFO: Cards removed from the board and inserted in " + turnManager.getCurrentPlayer() + "'s bookshelf in column " + message.getSelectedColumn());

            //Sending positive feedback to the player with the updated bookshelf
            viewHashMap.get(message.getSender()).showUpdatedBookshelf(game.getPlayer(turnManager.getCurrentPlayer()).getBookshelf().getBookshelf());
            viewHashMap.get(message.getSender()).sendGenericResponse(true, "Insertion successful!");

            //End turn housekeeping routine
            endTurn();
        }
        else {
            //Sending negative feedback to the player with the bookshelf without changes
            viewHashMap.get(message.getSender()).sendGenericResponse(false, "Invalid column! Please select another!" );
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

        //Checking if the board has to be refilled. If so, broadcasting the updated board to all the players
        if(game.checkRefill()){
            broadcastMessage(MessageType.REFILLED_BOARD);
            System.out.println("INFO: Board refilled");
        }

        //Checking if the current player has achieved any common goal
        game.scoreCommonGoal(turnManager.getCurrentPlayer());
        broadcastMessage(MessageType.COMMON_GOAL);

        //Checking if the bookshelf of the current player got full
        if(game.isPlayerBookshelfFull(turnManager.getCurrentPlayer()) && !turnManager.inEndGame()){
            turnManager.startEndGame();
            System.out.println("INFO: " + turnManager.getCurrentPlayer() + " filled their bookshelf. Endgame started");

            //Adding the bonus point to the first player who filled their bookshelf and removing the token from the board
            game.getPlayer(turnManager.getCurrentPlayer()).addScore(1);
            broadcastMessage(MessageType.TOKEN);
        }

        //Checking if the current player was the last one who had to play a turn, if so, starting the endgame, otherwise calling for the next player
        if(!turnManager.nextTurn()) {
            findWinner();

            //Starting the endgame routine
            lobby.endGame();
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
        Scoreboard scoreboard = game.orderByScore();

        //Broadcasting the scoreboard to all the players
        broadcastMessage(MessageType.SCOREBOARD, scoreboard);
    }
    //endregion

}
