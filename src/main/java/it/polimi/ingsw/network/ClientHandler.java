/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.util.ToolXML;
import it.polimi.ingsw.util.Player;
import it.polimi.ingsw.mechanics.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{
    private final Socket socket;

    //ObjectXStream because Server and Client will only exchange serialized Objects between themselves
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private int playerID;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        //TODO: gestire assegnazione identificativo. Serve che il server assegni un ID?
        //playerID = id;

        /*
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */

    }

    public void run(){

        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            while(true){
                boolean closeConn = false;
                if (in.hasNext()){
                    String lettura = in.nextLine();
                    //System.out.println(lettura);
                    switch (lettura.trim()) {
                        case "connect()" -> {
                            Player giocatore;
                            giocatore = ToolXML.xmlToPlayer(in.nextLine());
                            System.out.println("INFO: Player connesso:" + giocatore);
                        }

                        case "start()" -> {
                            System.out.println("INFO: Richiesta di avvio partita");
                            Game g = new Game(3);
                        }

                        case "quit()" -> {
                            System.out.println("INFO: Connessione al server chiusa.");
                            closeConn = true;
                        }
                    }
                }
                if(closeConn) break;
            }
            in.close();
            out.close();
            closeConnection();
        }
        catch (IOException e) {System.err.println(e.getMessage());}
    }

    public void sendMessage(Message message){
        try {
            outputStream.writeObject(message);
            outputStream.reset(); //anziché flush
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        try{
            socket.close();
            System.out.println("Player disconnected.");
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}
