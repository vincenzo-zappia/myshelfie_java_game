/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket socket;

    //ObjectXStream because Server and Client will only exchange serialized Objects between themselves
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void run(){

    }

    public void sendMessage(Message message){
        try {
            objOut.writeObject(message);
            objOut.reset(); //anziché flush
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO check method below
    public Message receiveMessage() {
        try {
            return (Message) objIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Classe non trovata");
        }
        return null;
    }

    public void closeConnection(){

    }
}
