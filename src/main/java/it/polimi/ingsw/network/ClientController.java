package it.polimi.ingsw.network;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.client2server.CreateLobbyRequest;
import it.polimi.ingsw.network.messages.client2server.InsertionRequest;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyRequest;
import it.polimi.ingsw.network.messages.client2server.SelectionRequest;
import it.polimi.ingsw.network.messages.client2server.StartGameRequest;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.UserInterface;

import java.util.ArrayList;

//TODO: Per l'impacchettamento messaggi serve implementare un nuovo tipo di Observer che cambia i parametri di implementazione update
public class ClientController implements Observer {
    //region ATTRIBUTES
    private final UserInterface view; //either CLI or GUI for the packing of messages User interface -> Server
    private Client client; //for the unpacking of messages Server -> User interface
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
    //TODO: Le chiamate dei metodi astratti della view che prendono parametri diversi da Message avverrà con message.getX()
    /**
     * Manages the reception of the messages from Client (therefore Server) and outputs them to CLI/GUI by calling View methods
     * @param message message received from Client
     */
    @Override
    public void update(Message message){

        switch (message.getType()){
            case CREATED_LOBBY -> {
                LobbyCreationMessage newLobby = (LobbyCreationMessage) message;
                if (newLobby.isSuccessful()) {
                    this.lobbyId = newLobby.getLobbyId();
                    view.showSuccessfulConnection(lobbyId);
                }
                else {}
            }
            case GENERIC_RESPONSE -> {
                GenericResponse response = (GenericResponse) message;
                view.sendGenericResponse(response.getResponse(), response.getContent());
            }
            case NEW_CONNECTION -> {
                UsernameListMessage connectionMessage = (UsernameListMessage) message;
                view.refreshConnectedPlayers(connectionMessage.getUsernameList());
            }
            case CURRENT_PLAYER -> view.showCurrentPlayer(message.getContent());
            case COORDINATES_CHECK -> {
                CoordinatesMessage checkedCoordinates = (CoordinatesMessage) message;
                view.sendCheckedCoordinates(checkedCoordinates.getCoordinates());
            }
            case BOOKSHELF_UPDATE -> {
                BookshelfMessage updatedBookshelf = (BookshelfMessage) message;
                view.sendUpdatedBookshelf(updatedBookshelf.getBookshelf());
            }
            case REFILLED_BOARD -> {
                BoardMessage boardUpdate = (BoardMessage) message;
                view.showRefilledBoard(boardUpdate.getBoardCells());
            }
            case ERROR_MESSAGE -> {
                ErrorMessage error = (ErrorMessage) message;
                view.showError(error.getContent());
            }
            case REMOVED_CARDS -> {
                CoordinatesMessage removedCards = (CoordinatesMessage) message;
                view.showRemovedCards(removedCards.getCoordinates());
            }
            case GOALS_DETAILS -> {
                GoalsMessage goalsMessage = (GoalsMessage) message;
                view.sendGoals(goalsMessage.getCommonGoals(), goalsMessage.getPrivateGoal());
            }
        }
    }
    //endregion

    //region CLIENT2SERVER
    /**
     * Creates and sends the Message that prompts the server to create a new lobby
     * @param username of the player who creates the lobby (he will be the couch aka the game master)(?)
     */
    public void createLobby(String username){
        Message create = new CreateLobbyRequest(username);
        this.username = username;
        client.sendMessage(create);
    }

    /**
     * Creates and sends a (Message) user request to join a lobby
     * @param username of the player who wants to join the lobby
     * @param lobbyId identification number of the lobby that the player wants to join
     */
    public void joinLobby(String username, int lobbyId){
        Message join = new JoinLobbyRequest(username, lobbyId);
        this.lobbyId = lobbyId;
        this.username = username;
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

    //TODO: Metodi impacchettamento messaggi. Outsource con creazione di interfaccia parallela a Observer con diversi tipi di implementazione del metodo update o locale?

    /**
     * Creates a Message out of the ordered cards and the column for their insertion chosen by the user and sends them to the server
     * @param selected ordered cards previously selected by the user
     * @param column where the selected cards will be inserted
     */
    public void sendInsertion(ArrayList<Card> selected, int column){
        Message insert = new InsertionRequest(this.username, selected, column);
        client.sendMessage(insert);
    }
    //endregion

}
