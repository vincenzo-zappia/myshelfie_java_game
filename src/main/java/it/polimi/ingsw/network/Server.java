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
    private ServerSocket serverSocket;
    private HashMap<Integer, Lobby> lobbyMap;
    private final ArrayList<String> usernameList;
    //endregion

    //region CONSTRUCTOR
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            lobbyMap = new HashMap<>();
            System.out.println("INFO: Server listening @ " + serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
        }

        usernameList = new ArrayList<>();
    }
    //endregion

    //region MAIN
    public static void main(String[] args) {
        Server server = new Server(2023);
        server.startConnection();

    }
    //endregion

    //region METHODS
    /**
     * Starts the connection between server and client
     */
    public void startConnection() {
        ExecutorService executor = Executors.newCachedThreadPool();

        //While loop to wait for and accept incoming connections
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("INFO: Trying to connect...");

                //Creating a thread for the management of the communication with connected client
                executor.submit(new ClientHandler(this, socket));
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
     * Creates a lobby as requested by player
     * @return the new initialized lobby
     */
    public Lobby createLobby() {

        //Selection of the id of the lobby to create
        int id;

        //If it's the first lobby to be created its id is chosen to be '1' ...
        if(lobbyMap.size() == 0) id = 1;

        //... otherwise the new id is set to be the id of the last created lobby plus one
        else id = (int) lobbyMap.keySet().toArray()[lobbyMap.size() - 1] + 1;

        //Creation of the actual lobby with the previously selected id
        Lobby lobby = new Lobby(this, id);
        lobbyMap.put(id, lobby);
        System.out.println("INFO: Lobby created.");
        return lobby;

    }

    /**
     * Remove specified lobby from the server
     * @param lobbyId to identify the lobby to delete
     */
    public synchronized void removeLobby(int lobbyId){
        for(String x: lobbyMap.get(lobbyId).getUsernameList()) removeUsername(x);
        lobbyMap.remove(lobbyId);
    }

    /**
     * Checks that the lobby hashmap contains a certain ID
     * @param lobbyId that needs to be checked
     * @return if the map contains the ID
     */
    public synchronized boolean existsLobby(int lobbyId){
        return lobbyMap.containsKey(lobbyId);
    }

    /**
     * Adds the username to the server list
     * @param username to check
     */
    public synchronized void addUsername(String username){
        usernameList.add(username);
    }

    /**
     * Checks that new player's choosed username isn't already in use
     * @param username to check
     * @return true if it's already present
     */
    public synchronized boolean existsUsername(String username) {
        for(String user : usernameList) if(user.equals(username)) return true;
        return false;
    }

    /**
     * Removes a username from the username list
     * @param username that needs to be removed
     */
    public void removeUsername(String username){
        usernameList.remove(username);
    }

    public synchronized Lobby getLobby(int lobbyId){
        return lobbyMap.get(lobbyId);
    }
    //endregion

}
