package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.mechanics.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.server2client.GenericMessage;
import it.polimi.ingsw.network.messages.server2client.TextResponse;
import it.polimi.ingsw.network.messages.server2client.SpecificResponse;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Lobby with an instance of the game
 */
public class Lobby {

    //region ATTRIBUTES
    private final int lobbyID;
    private final Server server;
    private final ArrayList<String> usernameList;
    private final HashMap<String, NetworkPlayer> networkMap;
    private GameController gameController;
    private boolean inGame;
    //endregion

    //region CONSTRUCTOR
    public Lobby(Server server, int lobbyID) {
        inGame = false;
        this.lobbyID = lobbyID;
        this.server = server;
        usernameList = new ArrayList<>();
        networkMap = new HashMap<>();
    }
    //endregion

    //region METHODS

    /**
     * Method used by clients to enter a lobby
     * @param netPlayer player that request access to lobby
     */
    public boolean joinLobby(NetworkPlayer netPlayer){
        Message response;

        //Checking if the selected lobby is already running a game
        if(inGame){
            netPlayer.getClientHandler().sendMessage(new TextResponse(false, "Game already started!"));
            netPlayer.getClientHandler().sendMessage(new SpecificResponse(false, MessageType.ACCESS_RESPONSE));
            return false;
        }

        //Checking if the lobby is full
        if(usernameList.size() >= 4){
            netPlayer.getClientHandler().sendMessage(new TextResponse(false, "Lobby is full!"));
            netPlayer.getClientHandler().sendMessage(new SpecificResponse(false, MessageType.ACCESS_RESPONSE));
            return false;
        }

        //Adding the player to the lobby
        String username = netPlayer.getUsername();
        usernameList.add(username);
        VirtualView view = new VirtualView(netPlayer.getClientHandler());
        netPlayer.setVirtualView(view);
        networkMap.put(username, netPlayer);
        System.out.println("INFO: Player joined successfully");
        return true;
    }

    /**
     * Broadcasts a message to all the players in the lobby (same as broadcast() in GameController)
     * @param message message to send
     */
    public void lobbyBroadcastMessage(Message message){
        for(NetworkPlayer player: networkMap.values()){
            player.getClientHandler().sendMessage(message);
        }
    }

    /**
     * Creates and initializes all the data structures needed for the creation of an actual new game. The game has now
     * officially started.
     */
    public void startGame(){

        //TODO: Decommentare (commentato solo per debug)
        /*
        //Checking if the number of the players is legal before initializing the game
        if(!(usernameList.size() > 1 && usernameList.size() <= 4)) {
            lobbyBroadcastMessage(new TextResponse(false, "Not enough players!"));
            lobbyBroadcastMessage(new SpecificResponse(false, MessageType.START_GAME_RESPONSE));
            return;
        }

         */
        lobbyBroadcastMessage(new SpecificResponse(true, MessageType.START_GAME_RESPONSE));

        //Creating GameController and the hashmap <PlayerUsername, VirtualView> through which GameController will manage the sending of messages from server to client
        HashMap<String, VirtualView> viewHashMap = new HashMap<>();
        for(NetworkPlayer netPlayer: networkMap.values()) viewHashMap.put(netPlayer.getUsername(), netPlayer.getVirtualView());
        gameController = new GameController(new Game(usernameList), viewHashMap);

        //Officially starting the game
        inGame = true;
        lobbyBroadcastMessage(new TextResponse(true, "Now in game!"));
        lobbyBroadcastMessage(new SpecificResponse(true, MessageType.ACCESS_RESPONSE));
        System.out.println("INFO: Game started");
    }

    /**
     * Forwards an external message to the GameController
     * @param message message to forwards
     */
    public void sendToGame(Message message){
        gameController.messageHandler(message);
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public ArrayList<String> getUsernameList(){
        return usernameList;
    }
    //endregion

}
