package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Subject;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class that manages only the network functionality of the Client (send and receive message,
 * server connection, ...)
 */
public class Client extends Subject implements Runnable{

    //region ATTRIBUTES
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    private Socket socket;

    //endregion

    //region CONSTRUCTOR
    public Client(String ip, int port){

        try {

            socket = new Socket(ip, port);
            objOut = new ObjectOutputStream(socket.getOutputStream());
            objIn = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    //endregion

    /**
     * The method receiveMessage() of Client is called in loop by the CLI/GUI for the whole duration of the game
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){

            //Actual management of the received message relatively to the state of Client
            notifyObserver(receiveMessage());
        }
    }

   public void sendMessage(Message msg){
       try {
           objOut.writeObject(msg);
           objOut.flush();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    /**
     * Waits the reception of a message and manages it accordingly to the state of Client
     */
    public Message receiveMessage(){
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

    public void forceDisconnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            System.out.println("INFO: ??? we are closed");
        }
    }

    //endregion

}
