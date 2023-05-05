/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.client2server.JoinLobbyMessage;
import it.polimi.ingsw.network.messages.server2client.ErrorMessage;
import it.polimi.ingsw.network.messages.server2client.ResponseMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{

    //region ATTRIBUTES
    private final Socket socket;
    private Server server;
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

    public void run(){
        initializeLobbyConnection();

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
     *
     */
    private void initializeLobbyConnection() {
        Message msg = receiveMessage();

        switch (msg.getType()){
            case JOIN_LOBBY -> {
                JoinLobbyMessage joinLobbyMessage = (JoinLobbyMessage) msg;
                if (server.existsLobby(joinLobbyMessage.getLobbyId())) this.lobby = server.getLobby(joinLobbyMessage.getLobbyId());
                lobby.joinLobby(new NetworkPlayer(msg.getUsername(), this));
                sendMessage(new ResponseMessage(MessageType.LOBBY_ACCESS_RESPONSE, true));
            }
            case CREATE_LOBBY -> {
                this.lobby = server.createLobby();
                lobby.joinLobby(new NetworkPlayer(msg.getUsername(), this));
                sendMessage(new ResponseMessage(MessageType.LOBBY_CREATION_RESPONSE, true));
                startGameHandler();
            }
            default -> {
                //TODO: generare eccezione?
            }
        }

    }

    /**
     *
     */
    private void startGameHandler() {
        Message msg = receiveMessage();
        if (msg.getType() == MessageType.START_GAME){
            lobby.startGame();
        }
        else {
            sendMessage(new ErrorMessage("received " + msg.getType() + " instead of START_GAME message"));
        }
    }



}
