package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * General functionalities of both server and client side network interfaces
 */
public abstract class NetworkInterface {

    //region ATTRIBUTES
    private final Socket socket;
    private final ObjectOutputStream objOut;
    private final ObjectInputStream objIn;
    //endregion

    //region CONSTRUCTOR
    public NetworkInterface(Socket socket) {
        this.socket = socket;

        try {
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region METHODS

    /**
     * Algorithm for the forwarding of one message (TCP/IP)
     * @param message the message to send
     */
    public void sendMessage(Message message) {
        try {
            objOut.writeObject(message);
            objOut.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the socket and interrupts the thread
     */
    public void safeDisconnect() {

        //Closing the socket
        System.out.println("INFO: Closing the socket...");
        try {
            socket.close();
            System.out.println("INFO: Socket closed successfully. Shutting down...");
        } catch (IOException e) {
            System.out.println("INFO: Couldn't close socket. Shutting down...");
        }

        //Interrupting the thread
        Thread.currentThread().interrupt();
    }

    protected ObjectInputStream getObjectInput(){
        return objIn;
    }
    //endregion

}
