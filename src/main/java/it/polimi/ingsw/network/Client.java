package it.polimi.ingsw.network;

import it.polimi.ingsw.util.Player;
import it.polimi.ingsw.network.messages.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    //region ATTRIBUTES
    private final String ip;
    private final int port;
    private Socket socket;
    private Scanner socketIn;
    private PrintWriter socketOut;

    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private int playerID; //TODO: serve davvero usare playerID? Username non sufficiente?
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

        System.out.println("Inserire Username: ");
        client.player.setUsername(String.valueOf(System.in.read()));

        //TODO: Gestire IO con ObjectStream e non Scanner e Writer direttamente nel costruttore di Client (?)
        //region OLD
           // BufferedReader breader = new BufferedReader(new FileReader("XML/player.xml"));
           // Scanner stdin = new Scanner(System.in);
              //FileReader freader = new FileReader("player.xml");
           // String contenuto = breader.readLine();
        //endregion

        try {
            client.requestConnection(client.player);
            client.startGame();
            client.quitGame();
        }
        catch (IOException e) {System.err.println("Error: " + e.getMessage());}
    }
    //endregion

    //region METHODS
    public void requestConnection(Player player) throws IOException{
        socket = new Socket(ip, port);
        System.out.println("INFO: Connessione stabilita");

        //aggiungere string req come parametro e rimuovere player

        /*socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
                socketOut.println("connect()");
                socketOut.println(req);
                socketOut.flush();
        }
        catch (NoSuchElementException e){
            System.out.println("INFO: Connessione chiusa");
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }*/

        Scanner stdin = new Scanner(System.in);

        try{
            socketOut.println("connect()");
            objOut.writeObject(player);
            objOut.reset();
        }
        catch (NoSuchElementException e){
            System.out.println("INFO: Connessione chiusa");
            stdin.close();
            objIn.close();
            objOut.close();
            socket.close();
        }
    }
    public void startGame() throws IOException {
        StartGame message = new StartGame(player.getUsername(), MessageType.START_GAME);
        objOut.writeObject(message);
        objOut.reset();
        System.out.println("INFO: Avvio partita");
    }
    public void quitGame(){
        socketOut.println("quit()");
        socketOut.flush();
        System.out.println("INFO: Chiusura partita");
    }
    //endregion

}
