package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.state.ClientSelectionState;
import it.polimi.ingsw.state.TurnState;

import java.io.*;
import java.net.Socket;

/**
 * Class that manages only the network functionality of the Client (send and receive message,
 * server connection, ...)
 */
public class Client implements Runnable{

    //region ATTRIBUTES
    private final String ip;
    private final int port;
    private Socket socket;
    private TurnState turnState; //Attribute that mirrors the turn of the game
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

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

        this.port = port;
        this.ip = ip;
        turnState = new ClientSelectionState(this);
    }
    //endregion

    /**
     * The method receiveMessage() of Client is called in loop by the CLI for the whole duration of the game
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            receiveMessage();
        }
    }

   public void sendMessage(Message msg){
       try {
           objOut.writeObject(msg);
           objOut.reset();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

    /**
     * Waits the reception of a message and manages it accordingly to the state of Client
     */
    public void receiveMessage(){
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

        //Actual management of the received message relatively to the state of Client
        turnState.messageHandler(msg);
    }

    //endregion

    public void setClientState(TurnState turnState) {
        this.turnState = turnState;
    }


}
