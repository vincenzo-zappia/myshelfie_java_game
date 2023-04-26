package it.polimi.ingsw.network;
/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;

    public static void main(String[] args) {
        Server server = new Server(2024);
        server.startConnection();
    }

    public Server(int port) {this.port = port;}
    public void startConnection() {

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        //TODO: Creazione socket direttamente nel costruttore?
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("INFO: Server in ascolto sulla porta " + port);
        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
            return;
        }

        //connection acceptance loop
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("INFO: Tentativo di connessione...");
                executor.submit(new ClientHandler(socket)); //thread creation
            } catch (IOException ex) {
                break;
            }
        }
        executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
