/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package proj.polimi.testrj45;

import proj.polimi.testrj45.entities.Player;
import proj.polimi.testrj45.state.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerSideController implements Runnable{
    private final Socket socket;

    public ServerSideController(Socket socket) {this.socket = socket;}

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
                            System.out.println("INFO: Player connesso:" + giocatore.toString());
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

    public void closeConnection(){
        try{
            socket.close();
            System.out.println("Player disconnected.");
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}
