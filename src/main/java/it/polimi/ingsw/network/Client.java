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
    private Player player;

    //endregion

    //region CONSTRUCTOR
    public Client(String ip, int port){
        System.out.println("----Client----");

        try {
            socket = new Socket("localhost", 2024); //Fixed IP and port
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("IO Exception from Client constructor");
        }

        player = new Player();

        this.port = port;
        this.ip = ip;
    }
    //endregion

    //region MAIN
    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 2024);

        //TODO: Gestire IO con ObjectStream e non Scanner e Writer direttamente nel costruttore di Client (?)
    }
    //region METHODS



    public void startGame() throws IOException {
        StartGame message = new StartGame(player.getUsername(), MessageType.START_GAME);
        objOut.writeObject(message);
        objOut.reset();
        System.out.println("INFO: Avvio partita");
    }

   public void sendMsg(Message msg){
       try {
           objOut.writeObject(msg);
           objOut.reset();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
    //endregion

}
