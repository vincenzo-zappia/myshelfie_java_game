package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.client2server.CreateLobbyRequest;
import it.polimi.ingsw.network.messages.client2server.InsertionRequest;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyRequest;
import it.polimi.ingsw.network.messages.client2server.SelectionRequest;
import it.polimi.ingsw.network.messages.client2server.StartGameRequest;
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
    private final UserInterface view; //either CLI or GUI for the packing of messages User interface -> Server
    private final Client client; //for the unpacking of messages Server -> User interface
    private String username;
    private int lobbyId;
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
                GenericResponse response = (GenericResponse) message;
                view.sendGenericResponse(response.getResponse(), response.getContent());
            }
            case CHECKED_USERNAME -> {
                GenericMessage checkedUsername = (GenericMessage) message;

                //Setting the username of the player
                this.username = message.getContent();

                view.confirmUsername();
            }
            case LOBBY_ID -> {
                LobbyIDMessage joinedLobby = (LobbyIDMessage) message;

                //Setting of the ID of the newly created or joined lobby
                this.lobbyId = joinedLobby.getLobbyID();

                view.checkInGame();
            }
            case NEW_CONNECTION -> {
                UsernameListMessage connectionMessage = (UsernameListMessage) message;
                view.refreshConnectedPlayers(connectionMessage.getUsernameList());
            }
            case CURRENT_PLAYER -> view.showCurrentPlayer(message.getContent());
            case CHECKED_COORDINATES -> {
                CoordinatesMessage checkedCoordinates = (CoordinatesMessage) message;
                view.sendCheckedCoordinates(checkedCoordinates.getCoordinates());
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
            case GOALS_DETAILS -> {
                GoalsMessage goalsMessage = (GoalsMessage) message;
                view.showGoalsDetails(goalsMessage.getCommonGoals(), goalsMessage.getPrivateGoal());
            }
            case SCOREBOARD -> {
                ScoreboardMessage scoreboard = (ScoreboardMessage) message;
                view.showScoreboard(scoreboard.getScoreboard());
            }
        }
    }
    //endregion

    //region CLIENT2SERVER
    public void checkUsername(String username){
        Message message = new GenericMessage(MessageType.USERNAME_REQUEST, username);
        client.sendMessage(message);
    }

    /**
     * Creates and sends the Message that prompts the server to create a new lobby
     * @param username of the player who creates the lobby (he will be the couch aka the game master)(?)
     */
    public void createLobby(String username){
        Message create = new CreateLobbyRequest(username);
        client.sendMessage(create);
    }

    /**
     * Creates and sends a (Message) user request to join a lobby
     * @param username of the player who wants to join the lobby
     * @param lobbyId identification number of the lobby that the player wants to join
     */
    public void joinLobby(String username, int lobbyId){
        Message join = new JoinLobbyRequest(username, lobbyId);
        client.sendMessage(join);
    }

    /**
     * Creates and sends the Message that once received by the server will start the game
     */
    public void startGame(){
        StartGameRequest start = new StartGameRequest(username);
        client.sendMessage(start);
    }

    /**
     * Creates a Message out of the coordinates of the cards selected by the user and sends them to the server
     * @param coordinates of the cards selected by the user
     */
    public void sendSelection(int[][] coordinates){
        SelectionRequest selectionRequest = new SelectionRequest(username, coordinates);
        client.sendMessage(selectionRequest);
    }

    /**
     * Creates a Message out of the ordered cards and the column for their insertion chosen by the user and sends them to the server
     * @param column where the selected cards will be inserted
     */
    public void sendInsertion(int column){
        Message insert = new InsertionRequest(this.username, column);
        client.sendMessage(insert);
    }
    //endregion

}
