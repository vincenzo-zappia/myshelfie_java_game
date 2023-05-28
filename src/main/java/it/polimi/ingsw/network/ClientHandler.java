/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyRequest;
import it.polimi.ingsw.network.messages.client2server.NewGameRequest;
import it.polimi.ingsw.network.messages.server2client.*;

import java.io.IOException;
import java.net.Socket;

/**
 * Controller that manages the reception and the sending of messages between the server and a specific client
 */
public class ClientHandler extends NetworkInterface implements Runnable{

    //region ATTRIBUTES
    private final Server server;
    private Lobby lobby;
    boolean newGame;
    //endregion

    //region CONSTRUCTOR
    public ClientHandler(Server server, Socket socket) {
        super(socket);
        this.server = server;
        newGame = true;
    }
    //endregion

    //region METHODS
    /**
     * Waits for incoming messages from client to server
     */
    public void run() {

        //Receiving the request to check username availability
        checkUsernameHandler();

        do {
            //Receiving the request either to create a lobby or join an existing one
            joinLobbyHandler(); //TODO: Gestire caso disconnessione lobby

            //Starting to receive all the possible game commands once the game has officially started
            try {
                //While loop to wait the reception of messages
                while (lobby.isLobbyOnline()) {
                    Message msg = (Message) getObjectInput().readObject();
                    System.out.println("INFO: Message received");
                    if (msg != null){

                        //TODO: Possibilità di iniziare nuovo game anche dopo disconnessione/fine partita
                        //Deciding whether to keep playing or quitting the application
                        if(msg.getType() == MessageType.NEW_GAME_REQUEST) {
                            NewGameRequest newGameRequest = (NewGameRequest) msg;
                            newGame = newGameRequest.getNewGame();
                        }
                        else lobby.sendToGame(msg); //Forwarding the game command to the game
                    }
                }
            }
            catch (IOException | ClassNotFoundException e) {

                //Safe disconnection
                System.out.println("INFO: A player has disconnected. Closing the game...");
                lobby.endGame();
                lobby.forceDisconnection(this);
            }
        } while(newGame);
        safeDisconnect();
    }

    /**
     * Checks if the username selected by the player is available
     */
    private void checkUsernameHandler(){
        Message message = receiveMessage();

        if(message.getType() == MessageType.USERNAME_REQUEST){
            if(!server.existsUsername(message.getContent()) && !message.getContent().replace(" ", "").equals("")) {

                //Sending positive feedback back to the player
                sendMessage(new TextResponse(true, "Username available!"));
                sendMessage(new SpecificResponse(true, message.getContent(), MessageType.CHECKED_USERNAME));

                //Adding the username to the server username list
                server.addUsername(message.getContent());
            }
            else {
                sendMessage(new TextResponse(false, "Username unavailable!"));
                sendMessage(new SpecificResponse(false, MessageType.CHECKED_USERNAME));
                checkUsernameHandler();
            }
        }
    }

    /**
     * Either creates a new lobby or checks if the player can join the selected existing one
     */
    private void joinLobbyHandler() {
        Message message = receiveMessage();

        switch(message.getType()){

            //Creation of a new lobby
            case CREATE_LOBBY_REQUEST ->  {

                //Creating a new lobby
                this.lobby = server.createLobby();

                //Joining the newly created lobby and sending back the lobby ID
                lobby.joinLobby(new NetworkPlayer(message.getSender(), this));
                sendMessage(new TextResponse(true, "Creation successful!"));
                sendMessage(new GenericMessage("Lobby ID: " + lobby.getLobbyID(), MessageType.CREATION_RESPONSE));

                //Showing the newly created lobby
                lobby.lobbyBroadcastMessage(new UsernameListMessage(lobby.getUsernameList()));

                //Initialization of the game
                startGameHandler();
            }

            //Checking if the player can join the selected lobby
            case JOIN_LOBBY_REQUEST -> {
                JoinLobbyRequest joinLobbyRequest = (JoinLobbyRequest) message;

                //Checking if the selected lobby exists
                if(server.existsLobby(joinLobbyRequest.getLobbyId())) this.lobby = server.getLobby(joinLobbyRequest.getLobbyId());
                else {
                    sendMessage(new TextResponse(false, "This lobby doesn't exist!"));
                    sendMessage(new SpecificResponse(false, null, MessageType.ACCESS_RESPONSE));
                    joinLobbyHandler();
                    return; //Return to stop the recursion
                }

                /*
                 * Checking if the username chosen by the player is already taken, if the lobby has reached max capacity
                 * and if the game has already started, if not, joining the lobby
                 */
                if(lobby.joinLobby(new NetworkPlayer(message.getSender(), this))){
                    sendMessage(new TextResponse(true, "Join successful!"));
                    sendMessage(new SpecificResponse(true, "Lobby ID: " + lobby.getLobbyID() ,MessageType.ACCESS_RESPONSE));


                    //Sending to all the players the updated username list with the new entry
                    lobby.lobbyBroadcastMessage(new UsernameListMessage(lobby.getUsernameList()));
                }
                else joinLobbyHandler();

            }

            default -> sendMessage(new TextResponse(false, "Invalid command!"));
        }

    }

    /**
     * Waits for the lobby master to send a start game command to prompt the lobby to create the actual new game
     * (initialization of all the data structures needed for the game to work)
     */
    private void startGameHandler() {
        Message message = receiveMessage();

        switch (message.getType()){
            case START_GAME_REQUEST -> lobby.startGame();

            //Prompting the lobby master to choose between starting a new game or quitting the application after a player has disconnected from the lobby
            case NEW_GAME_REQUEST -> {
                NewGameRequest newGameRequest = (NewGameRequest) message;
                newGame = newGameRequest.getNewGame();
            }
            default -> {
                sendMessage(new TextResponse(false, "The game has to start first!"));
                startGameHandler();
            }
        }

    }

    /**
     * Algorithm for the reception of one message (TCP/IP)
     * @return the received message
     */
    protected Message receiveMessage(){
        boolean received = false;
        Message message = null;
        try {
            while(!received){
                message = (Message) getObjectInput().readObject();
                received = message != null;
            }
        } catch (IOException | ClassNotFoundException e) {

            //Safe disconnection
            System.out.println("INFO: A player has disconnected. Closing the game...");
            lobby.endGame();
            lobby.forceDisconnection(this);
        }
        return message;
    }
    //endregion

}
