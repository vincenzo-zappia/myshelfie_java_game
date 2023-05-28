package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.Subject;

import java.io.*;
import java.net.Socket;

import static java.lang.System.exit;

/**
 * Manages the client side network functionalities
 */
public class Client extends NetworkInterface implements Runnable, Subject {

    //region CONSTRUCTOR
    public Client(String ip, int port) throws IOException {
       super(new Socket(ip, port));
    }
    //endregion

    //region METHODS
    /**
     * Receives messages in loop and forwards them to the client controller
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) notifyObserver(receiveMessage());
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
            System.out.println("INFO: Server connection crashed. Shutting down...");
            exit(0);
        }
        return message;
    }

    /**
     * Interrupts the client thread (without closing the socket)
     */
    public void closeClient(){
        Thread.currentThread().interrupt();
    }
    //endregion

    //region OBSERVER
    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver(Message message) {
        for(Observer o : observers){
            o.update(message);
        }
    }
    //endregion

}
