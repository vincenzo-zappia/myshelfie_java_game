/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyRequest;
import it.polimi.ingsw.network.messages.server2client.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Controller that manages the reception and the sending of messages between the server and a specific client
 */
public class ClientHandler implements Runnable{

    //region ATTRIBUTES
    private final Socket socket;
    private final Server server;
    private Lobby lobby;
    private final ObjectOutputStream objOut;
    private final ObjectInputStream objIn;
    //endregion

    public ClientHandler(Server server, Socket socket) {
        this.socket = socket;
        this.server = server;

        try {
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        //First message that the server can receive is a username check //TODO: Revisionare commento
        /*
         * The first message that the server can receive from the client is a connection message that prompts the server
         * to either create a new lobby or add the client to an existing one //TODO: Revisionare commento
         */
        joinLobbyHandler();

        System.out.println("INFO: Game phase started");
        //Forwarding all the possible commands sent by the player to GameController after the game has started
        try {
            //While loop to wait the reception of messages
            while(!Thread.currentThread().isInterrupted()){
                Message msg = (Message) objIn.readObject();
                System.out.println("INFO: Message received");
                if(msg != null) lobby.sendToGame(msg);
            }
            System.out.println("INFO: thread interrupted");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("INFO: ex");
            throw new RuntimeException(e);

        }
    }

    //TODO: Implementare safe disconnect
    //region METHODS

    /**
     * Sends a message from the server to the client (TCP/IP)
     * @param message sent message
     */
    public void sendMessage(Message message){
        try {
            objOut.writeObject(message);
            objOut.reset(); //anziché flush
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Either creates a new lobby or checks if the player can join the selected existing one
     */
    private void joinLobbyHandler() {
        System.out.println("INFO: Connection phase started");

        Message message = receiveOneMessage();

        //Username check //todo: revisionare commento
        if(!server.existsUsername(message.getUsername())){
            sendMessage(new SpecificResponse(false, "Username unavailable, choose another!", MessageType.ACCESS_RESPONSE));
            joinLobbyHandler();
        }

        switch(message.getType()){

            //Creation of a new lobby
            case CREATE_LOBBY_REQUEST -> {

                //Creating a new lobby
                this.lobby = server.createLobby();

                //Joining the newly created lobby
                lobby.joinLobby(new NetworkPlayer(message.getUsername(), this));
                sendMessage(new GenericResponse(true, "Creation successful!\nLobby ID: " + lobby.getLobbyID()));
                sendMessage(new LobbyIDMessage(lobby.getLobbyID()));

                //Initialization of the game
                //sendMessage(new GenericResponse(true, "Type *start* when you want to start the game!"));
                startGameHandler();
            }

            //Checking if the player can join the selected lobby
            case JOIN_LOBBY_REQUEST -> {
                JoinLobbyRequest joinLobbyRequest = (JoinLobbyRequest) message;

                //Checking if the selected lobby exists
                if(server.existsLobby(joinLobbyRequest.getLobbyId())) this.lobby = server.getLobby(joinLobbyRequest.getLobbyId());
                else {
                    sendMessage(new SpecificResponse(false, "This lobby doesn't exist!", MessageType.ACCESS_RESPONSE));
                    joinLobbyHandler();
                }

                /*
                 * Checking if the username chosen by the player is already taken, if the lobby has reached max capacity
                 * and if the game has already started, if not, joining the lobby
                 */
                if(lobby.joinLobby(new NetworkPlayer(message.getUsername(), this))){ //TODO: Crea comunque netplayer, non creare prima del check?
                    sendMessage(new LobbyIDMessage(lobby.getLobbyID()));
                    sendMessage(new SpecificResponse(true, "Join successful!", MessageType.ACCESS_RESPONSE));
                }
                else joinLobbyHandler();

                //Sending to all the players the updated username list with the new entry
                lobby.lobbyBroadcastMessage(new UsernameListMessage(lobby.getUsernameList()));

            }

            default -> {
                sendMessage(new GenericResponse(false, "Invalid command!"));
            }
        }

    }

    /**
     * It waits for the lobby master to send a start game command to prompt the lobby to create the actual new game
     * (initialization of all the data structures needed for the game to work)
     */
    private void startGameHandler() {
        System.out.println("INFO: Waiting START message");
        Message message = receiveOneMessage();

        if (message.getType() == MessageType.START_GAME_REQUEST){
            lobby.startGame();
        }
        else {
            sendMessage(new SpecificResponse(false, "The game has to start first!", MessageType.ACCESS_RESPONSE));
        }
    }

    /**
     * Algorithm for the reception of one message
     * @return the message received
     */
    private Message receiveOneMessage(){
        boolean received = false;
        Message message = null;
        try {
            while(!received){
                message = (Message) objIn.readObject();
                received = message != null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return message;
    }
    //endregion

}
