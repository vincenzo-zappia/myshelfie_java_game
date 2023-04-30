/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.JoinLobby;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

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
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        initializeLobbyConnection();
        Message msg = receiveMessage(!Thread.currentThread().isInterrupted());
        lobby.sendToController(msg);

    }

    private void initializeLobbyConnection() {
        Message msg = receiveMessage();

        switch (msg.getType()){
            case JOIN_LOBBY -> {
                JoinLobby joinLobby = (JoinLobby) msg;
                if (server.existsLobby(joinLobby.getLobbyId())) this.lobby = server.getLobby(joinLobby.getLobbyId());
                lobby.joinLobby(new NetworkPlayer(msg.getUsername(), this));
            }
            case CREATE_LOBBY -> {
                this.lobby = server.createLobby();
                lobby.joinLobby(new NetworkPlayer(msg.getUsername(), this));
                startGameHandler();
            }
            default -> {
                //TODO: generare eccezione?
            }
        }

    }

    private Message receiveMessage(boolean... conditions){
        boolean res = false;
        Message msg = null;
        try {
            while(!res && conditions[0]){
                msg = (Message) objIn.readObject();
                res = msg!=null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }

    private void startGameHandler() {
        Message msg = receiveMessage();
        if (msg.getType() == MessageType.START_GAME){
            lobby.startGame();
        }
        else {
            //TODO: invio messaggio di errore
        }
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

}
