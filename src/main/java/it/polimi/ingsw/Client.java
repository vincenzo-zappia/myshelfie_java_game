package it.polimi.ingsw;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    //REGION ATTRIBUTES
    private final String ip;
    private final int port;
    private Socket socket;
    private Scanner socketIn;
    private PrintWriter socketOut;
    //END REGION

    //REGION CONSTRUCTOR
    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    //END REGION

    public static void main(String[] args) throws IOException {
        Client client = new Client("localhost", 2024);
        BufferedReader breader = new BufferedReader(new FileReader("XML/player.xml"));
        Scanner stdin = new Scanner(System.in);
//        FileReader freader = new FileReader("player.xml");
        String contenuto = breader.readLine();

        try {
            client.requestConnection(contenuto);
            client.startGame();
            client.quit();
        }
        catch (IOException e) {System.err.println("Error: " + e.getMessage());}
    }

    //REGION METHODS
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
    public void quit(){
        socketOut.println("quit()");
        socketOut.flush();
        System.out.println("INFO: Chiusura partita");
    }
    //END REGION

}
