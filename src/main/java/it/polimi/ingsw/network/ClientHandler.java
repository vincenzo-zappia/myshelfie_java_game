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

public class ClientHandler implements Runnable{

    //region ATTRIBUTES
    private final Socket socket;
    private final Server server;
    private Lobby lobby;

    //ObjectXStream because Server and Client will only exchange serialized Objects between themselves
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
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

    //TODO: Implementare safe disconnect
    public void run(){

        /*
         * The first message that the server can receive from the client is a connection message that prompts the server
         * to either create a new lobby or add the client to an existing one
         */
        initializeLobbyConnection();

        //Forwarding of all the possible commands sent by the player to GameController after the game is started
        try {
            while(!Thread.currentThread().isInterrupted()){
                Message msg = (Message) objIn.readObject();
                if(msg != null) lobby.sendToController(msg);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return
     */
    private Message receiveMessage(/*boolean... conditions*/){
        boolean res = false;
        Message msg = null;
        try {
            while(!res){
                msg = (Message) objIn.readObject();
                res = msg!=null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }

    /**
     * Invia i messaggi attraverso TCP/IP a/ai client
     * @param message rappresenta il messaggio da inviare
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
     * //TODO: Univocità del tipo del primo messaggio che un client può inviare
     */
    private void initializeLobbyConnection() {
        Message msg = receiveMessage();

        switch (msg.getType()){

            //Checking if the player can join the selected lobby
            case JOIN_LOBBY_REQUEST -> {
                JoinLobbyRequest joinLobbyRequest = (JoinLobbyRequest) msg;

                //Checking if the selected lobby exists
                if (server.existsLobby(joinLobbyRequest.getLobbyId())) this.lobby = server.getLobby(joinLobbyRequest.getLobbyId());
                else {
                    sendMessage(new GenericResponse(false, "This lobby doesn't exist!"));
                    initializeLobbyConnection();
                }

                /*
                 * Checking if the username chosen by the player is already taken, if the lobby has reached max capacity
                 * and if the game is already started
                 */
                if(lobby.joinLobby(new NetworkPlayer(msg.getUsername(), this))){
                    sendMessage(new GenericResponse(true, "Join successful!"));
                    sendMessage(new GenericMessage(MessageType.JOINED_LOBBY, ""));
                }
                else initializeLobbyConnection();

                //Sends to all the players the username list with the newly connected one
                lobby.sendLobbyMessage(new UsernameListMessage(lobby.getPlayerUsernames()));

            }

            //Creation of a new lobby
            case CREATE_LOBBY_REQUEST -> {

                //Creating a new lobby
                this.lobby = server.createLobby();

                //Joining the newly created lobby
                lobby.joinLobby(new NetworkPlayer(msg.getUsername(), this));
                sendMessage(new GenericResponse(true, "Lobby creation successful!\nLobby ID: " + lobby.getLobbyId()));
                sendMessage(new LobbyIDMessage(lobby.getLobbyId()));

                //Initialization of the game
                startGameHandler();
            }

            default -> {
                //TODO: generare eccezione?
            }
        }

    }

    /**
     * It waits for the lobby master to send a start game command to prompt the lobby to create the actual new game
     * (initialization of all the data structures needed for the game to work)
     */
    private void startGameHandler() {
        Message msg = receiveMessage();

        if (msg.getType() == MessageType.START_GAME_REQUEST){
            lobby.startGame();
        }
        else {
            sendMessage(new ErrorMessage("received " + msg.getType() + " instead of START_GAME message"));
        }
    }



}
