/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 20/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //region ATTRIBUTES
    private final int port;
    private ServerSocket serverSocket;
    private HashMap<Integer, Lobby> lobbyMap;
    //endregion

    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            lobbyMap = new HashMap<>();
            System.out.println("INFO: Server listening @ " + serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
        }
    }

    //region MAIN
    public static void main(String[] args) {
        Server server = new Server(2023);
        server.startConnection();

    }
    //endregion

    //region METHODS
    /**
     * Networking methods to start the connections
     * between server and clients
     */
    public void startConnection() {
        ExecutorService executor = Executors.newCachedThreadPool();

        //connection acceptance loop
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("INFO: Tentativo di connessione...");
                executor.submit(new ClientHandler(this, socket)); //thread creation
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

    /**
     * Check that the lobby hashmap contains a certain ID
     * @param lobbyId that needs to be checked
     * @return true if map contains the ID
     */
    public boolean existsLobby(int lobbyId){
        return lobbyMap.containsKey(lobbyId);
    }

    public Lobby getLobby(int lobbyId){
        return lobbyMap.get(lobbyId);
    }

    /**
     * Create lobby as requested by player
     * @return the new initialized lobby
     */
    public Lobby createLobby(){
        int id = lobbyMap.size()+1;
        Lobby lobby = new Lobby(this, id);
        lobbyMap.put(id, lobby);
        System.out.println("INFO: Lobby created.");
        return lobby;
    }

    /**
     * Remove specified lobby from the server
     * @param lobbyId to identify the lobby to delete
     */
    public void removeLobby(int lobbyId){
        lobbyMap.remove(lobbyId);
    }
    //endregion
}
