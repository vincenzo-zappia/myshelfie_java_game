package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.Subject;

import java.io.*;
import java.net.Socket;

/**
 * Class that manages only the network functionality of the Client (send and receive message,
 * server connection, ...)
 */
public class Client extends NetworkInterface implements Runnable, Subject {

    public Client(String ip, int port) throws IOException {
       super(new Socket(ip, port));
    }

    /**
     * The method receiveMessage() of Client is called in loop by the CLI/GUI for the whole duration of the game
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){

            //Actual management of the received message relatively to the state of Client
            notifyObserver(receiveOneMessage());
        }
    }

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
