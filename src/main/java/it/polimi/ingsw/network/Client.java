package it.polimi.ingsw.network;

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

    //private ObjectOutputStream outputStream;
    //private ObjectInputStream inputStream;
    //private int playerID;
    //endregion

    //region CONSTRUCTOR
    public Client(String ip, int port){
        System.out.println("----Client----");
        /*
        try {
            socket = new Socket("localhost", 2024); //Fixed IP and port
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("IO Exception from Client constructor");
        }
         */

        this.port = port;
        this.ip = ip;
    }
    //endregion

    //region MAIN
    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 2024);

        //TODO: Gestire IO con ObjectStream e non Scanner e Writer direttamente nel costruttore di Client (?)
        BufferedReader breader = new BufferedReader(new FileReader("XML/player.xml"));
        Scanner stdin = new Scanner(System.in);
        //FileReader freader = new FileReader("player.xml");
        String contenuto = breader.readLine();

        try {
            client.requestConnection(contenuto);
            client.startGame();
            client.quitGame();
        }
        catch (IOException e) {System.err.println("Error: " + e.getMessage());}
    }
    //endregion

    //region METHODS
    public void requestConnection(String req) throws IOException{
        socket = new Socket(ip, port);
        System.out.println("INFO: Connessione stabilita");

        socketIn = new Scanner(socket.getInputStream());
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
        }
    }
    public void startGame(){
        socketOut.println("start()");
        socketOut.flush();
        System.out.println("INFO: Avvio partita");
    }
    public void quitGame(){
        socketOut.println("quit()");
        socketOut.flush();
        System.out.println("INFO: Chiusura partita");
    }
    //endregion

}
