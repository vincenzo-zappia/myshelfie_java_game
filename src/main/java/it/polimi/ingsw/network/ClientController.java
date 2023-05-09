package it.polimi.ingsw.network;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.client2server.CreateLobbyMessage;
import it.polimi.ingsw.network.messages.client2server.InsertionMessage;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyMessage;
import it.polimi.ingsw.network.messages.client2server.SelectionMessage;
import it.polimi.ingsw.network.messages.client2server.StartGame;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.state.ClientSelectionState;
import it.polimi.ingsw.state.TurnState;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: Per l'impacchettamento messaggi serve implementare un nuovo tipo di Observer che cambia i parametri di implementazione update
public class ClientController implements Observer {
    //region ATTRIBUTES
    private TurnState turnState; //TODO: Decidere se rimuovere il design pattern
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

        //TODO: Decidere come gestire fase iniziale se da costruttore o dal primo messaggio inviato da server
        turnState = new ClientSelectionState(this);
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

        //TODO: Per il momento non uso lo state
        switch (message.getType()){
            case LOBBY_CREATION_RESPONSE -> {
                LobbyCreationResponse newLobby = (LobbyCreationResponse) message;
                view.showSuccessfulConnection(newLobby.getLobbyId());
            }
            case LOBBY_ACCESS_RESPONSE -> {
                LobbyAccessResponse access = (LobbyAccessResponse) message;
                view.showSuccessfulConnection(lobbyId);
            }
            case NEW_CONNECTION -> {
                NewConnectionMessage connectionMessage = (NewConnectionMessage) message;
                view.refreshConnectedPlayers(connectionMessage.getUsernameList());
            }
            case GAME_START -> {
                view.showConfirmation(MessageType.GAME_START);
            }
            case CURRENT_PLAYER -> {
                view.showCurrentPlayer(message.getContent());
            }
            case SELECTION_RESPONSE -> {
                ResponseMessage response = (ResponseMessage) message;
                if(response.getResponse()) view.showConfirmation(MessageType.SELECTION_RESPONSE);
            }
            case INSERTION_RESPONSE -> {
                InsertionResponseMessage response = (InsertionResponseMessage) message;
                System.out.println("INFO: " + response.getBookshelf().length + " " + response.getBookshelf()[0].length);
                System.out.println("INFO "+ Arrays.toString(response.getBookshelf()[5]));
                if(response.getResponse()) view.sendInsertionResponse(response.getBookshelf(), true);
            }
            case BOARD_REFILL -> {
                BoardRefillUpdate boardUpdate = (BoardRefillUpdate) message;
                view.showRefilledBoard(boardUpdate.getBoardCells());
            }
            case ERROR_MESSAGE -> {
                ErrorMessage error = (ErrorMessage) message;
                view.showError(error.getContent());
            }
            case CARD_REMOVAL -> {
                CardRemovalMessage remove = (CardRemovalMessage) message;
                view.refreshBoard(remove.getCoordinates());
            }

        }
        //TODO: Chiamata di metodi View per sputare su GUI/CLI
        turnState.messageHandler(message);
    }
    //endregion

    //region CLIENT2SERVER
    /**
     * Creates and sends the Message that prompts the server to create a new lobby
     * @param username of the player who creates the lobby (he will be the couch aka the game master)(?)
     */
    public void createLobby(String username){
        Message create = new CreateLobbyMessage(username);
        this.username = username;
        client.sendMessage(create);
    }

    /**
     * Creates and sends a (Message) user request to join a lobby
     * @param username of the player who wants to join the lobby
     * @param lobbyId identification number of the lobby that the player wants to join
     */
    public void joinLobby(String username, int lobbyId){
        Message join = new JoinLobbyMessage(username, lobbyId);
        this.lobbyId = lobbyId;
        this.username = username;
        client.sendMessage(join);
    }

    /**
     * Creates and sends the Message that once received by the server will start the game
     */
    public void startGame(){
        StartGame start = new StartGame(username);
        client.sendMessage(start);
    }

    /**
     * Creates a Message out of the coordinates of the cards selected by the user and sends them to the server
     * @param coordinates of the cards selected by the user
     */
    public void sendSelection(int[][] coordinates){
        SelectionMessage selectionMessage = new SelectionMessage(username, coordinates);
        client.sendMessage(selectionMessage);
    }

    //TODO: Metodi impacchettamento messaggi. Outsource con creazione di interfaccia parallela a Observer con diversi tipi di implementazione del metodo update o locale?

    /**
     * Creates a Message out of the ordered cards and the column for their insertion chosen by the user and sends them to the server
     * @param selected ordered cards previously selected by the user
     * @param column where the selected cards will be inserted
     */
    public void sendInsertion(ArrayList<Card> selected, int column){
        Message insert = new InsertionMessage(this.username, selected, column);
        client.sendMessage(insert);
    }
    //endregion

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }
}
