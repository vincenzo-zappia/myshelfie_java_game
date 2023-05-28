package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.client2server.*;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.UserInterface;

/**
 * Manages the messages received from the server by calling the methods of the user interface.
 * Creates and sends messages to the server from the user interface.
 */
public class ClientController implements Observer {

    //region ATTRIBUTES
    private final UserInterface view; //Either CLI or GUI
    private final Client client; //for the unpacking of messages Server -> User interface
    private String username;
    //endregion

    //region CONSTRUCTOR
    public ClientController(UserInterface view, Client client){
        this.view = view;
        this.client = client;
        client.register(this);
        new Thread(client).start();
    }
    //endregion

    //region SERVER2CLIENT
    /**
     * Manages the reception of the messages from Client (therefore Server) and outputs them to CLI/GUI by calling View methods
     * @param message message received from Client
     */
    @Override
    public void update(Message message){
        switch (message.getType()){
            case GENERIC_RESPONSE -> {
                TextResponse response = (TextResponse) message;
                view.sendGenericResponse(response.getResponse(), response.getContent());
            }
            case CHECKED_USERNAME -> {
                SpecificResponse response = (SpecificResponse) message;

                //Linking the verified username to the client
                this.username = response.getContent();

                view.confirmUsername(response.getResponse());
            }
            case CREATION_RESPONSE -> {
                GenericMessage creationResponse = (GenericMessage) message;
                view.confirmCreation(creationResponse.getContent());
            }
            case ACCESS_RESPONSE -> {
                SpecificResponse response = (SpecificResponse) message;
                view.confirmAccess(response.getResponse(), response.getContent());
            }
            case NEW_CONNECTION -> {
                UsernameListMessage connectionMessage = (UsernameListMessage) message;
                view.refreshConnectedPlayers(connectionMessage.getUsernameList());
            }
            case START_GAME_RESPONSE -> {
                SpecificResponse startGameResponse = (SpecificResponse) message;
                view.confirmStartGame(startGameResponse.getResponse());
            }
            case CURRENT_PLAYER -> view.showCurrentPlayer(message.getContent());
            case CHECKED_COORDINATES -> {
                CoordinatesMessage checkedCoordinates = (CoordinatesMessage) message;
                view.sendCheckedCoordinates(checkedCoordinates.isValid(), checkedCoordinates.getCoordinates());
            }
            case REMOVED_CARDS -> {
                CoordinatesMessage removedCards = (CoordinatesMessage) message;
                view.showRemovedCards(removedCards.getCoordinates());
            }
            case UPDATED_BOOKSHELF -> {
                BookshelfMessage updatedBookshelf = (BookshelfMessage) message;
                view.showUpdatedBookshelf(updatedBookshelf.getBookshelf());
            }
            case REFILLED_BOARD -> {
                BoardMessage boardUpdate = (BoardMessage) message;
                view.showRefilledBoard(boardUpdate.getBoardCells());
            }
            case COMMON_GOAL -> {
                CommonGoalsMessage commonGoalsMessage = (CommonGoalsMessage) message;
                view.showCommonGoals(commonGoalsMessage.getCommonGoals());
            }
            case PRIVATE_GOAL -> {
                PrivateGoalMessage privateGoalMessage = (PrivateGoalMessage) message;
                view.showPrivateGoal(privateGoalMessage.getPrivateGoal());
            }
            case SCOREBOARD -> {
                ScoreboardMessage scoreboard = (ScoreboardMessage) message;
                view.showScoreboard(scoreboard.getScoreboard());
            }
            case TOKEN -> {
                GenericMessage token = (GenericMessage) message;
                view.showToken(token.getContent());
            }
            case CHAT -> {
                ChatMessage chatMessage = (ChatMessage) message ;
                view.showChat(chatMessage.getContent());
            }
            case DISCONNECTION -> view.showDisconnection();
        }
    }
    //endregion

    //region CLIENT2SERVER
    /**
     * Creates and sends request to check the availability of a chosen username
     * @param username to check
     */
    public void checkUsername(String username){
        Message message = new CheckUsernameRequest(username);
        client.sendMessage(message);
    }

    /**
     * Creates and sends the request to create a new lobby
     */
    public void createLobby(){
        Message create = new CreateLobbyRequest(username);
        client.sendMessage(create);
    }

    /**
     * Creates and sends the request to join a lobby
     * @param lobbyId identification number of the lobby that the player wants to join
     */
    public void joinLobby(int lobbyId){
        Message join = new JoinLobbyRequest(username, lobbyId);
        client.sendMessage(join);
    }

    /**
     * Creates and sends the request to start the game
     */
    public void startGame(){
        StartGameRequest start = new StartGameRequest(username);
        client.sendMessage(start);
    }

    /**
     * Creates and sends the request to select the chosen cards
     * @param coordinates of the cards selected by the user
     */
    public void sendSelection(int[][] coordinates){
        SelectionRequest selectionRequest = new SelectionRequest(username, coordinates);
        client.sendMessage(selectionRequest);
    }

    /**
     * Creates and sends the request to add the previously selected cards into the specified column
     * @param column where the player wants to add the cards
     */
    public void sendInsertion(int column){
        Message insert = new InsertionRequest(username, column);
        client.sendMessage(insert);
    }

    /**
     * Creates and sends a chat text
     * @param chat text to send
     */
    public void sendChat(String chat){
        Message message = new ChatMessage(username, chat);
        client.sendMessage(message);
    }

    /**
     * Creates and sends the request to either start a new game or quit the application after the previous one has ended
     * @param newGame true is the players wants to keep playing, false if they want to quit the application
     */
    public void sendNewGame(boolean newGame){
        NewGameRequest newGameRequest = new NewGameRequest(username, newGame);
        client.sendMessage(newGameRequest);
    }

    /**
     * Forces the client to interrupt its thread if the player wants to quit the application
     */
    public void stopClientConnection(){
        client.closeClient();
    }
    //endregion

}
