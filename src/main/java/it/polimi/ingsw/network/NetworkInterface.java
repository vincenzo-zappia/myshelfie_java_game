package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.System.exit;

public abstract class NetworkInterface {

    //region ATTRIBUTES
    private final Socket socket;
    private final ObjectOutputStream objOut;
    private final ObjectInputStream objIn;
    //endregion

    public NetworkInterface(Socket socket) {
        this.socket = socket;

        try {
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Algorithm for the reception of one message
     * @return the message received
     */
    protected Message receiveOneMessage(){
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

    /**
     * Sends a message from server to client (TCP/IP)
     * @param message sent message
     */
    public void sendMessage(Message message) {
        try {
            objOut.writeObject(message);
            objOut.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void safeDisconnect() {
        try {
            socket.close();
            exit(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            System.out.println("INFO: ??? we are closed");
        }
    }

    protected ObjectInputStream getObjectInput(){
        return objIn;
    }

}
