package it.polimi.ingsw.network;

import it.polimi.ingsw.entities.Player;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.Message;

import java.io.*;
import java.net.Socket;

public class Client {

    //region ATTRIBUTES
    private final String ip;
    private final int port;
    private Socket socket;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;

    //endregion

    //region CONSTRUCTOR
    public Client(String ip, int port){

        try {
            socket = new Socket(ip, port);
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("IO Exception from Client constructor");
        }

        this.port = port;
        this.ip = ip;
    }
    //endregion

   public void sendMessage(Message msg){
       try {
           objOut.writeObject(msg);
           objOut.reset();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }

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
    //endregion

}
