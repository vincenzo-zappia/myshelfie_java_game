package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.mechanics.VirtualView;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.server2client.ErrorMessage;
import it.polimi.ingsw.network.messages.server2client.GenericResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    //region ATTRIBUTES
    private final int lobbyId;
    private final Server server;
    private final ArrayList<String> playerUsernames;
    private final HashMap<String, NetworkPlayer> networkMap;
    private boolean inGame;
    private GameController gameController;
    //endregion

    //region CONSTRUCTOR
    public Lobby(Server server, int lobbyId) {
        inGame = false;
        this.lobbyId = lobbyId;
        this.server = server;
        playerUsernames = new ArrayList<>();
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
        if(inGame) {
            response = new GenericResponse(false, "Game already started!");
            netPlayer.getClientHandler().sendMessage(response);
            return false;
        }

        //Checking if the lobby is full
        if(playerUsernames.size()>=4){
            response = new GenericResponse(false, "Lobby is full!");
            netPlayer.getClientHandler().sendMessage(response);
            return false;
        }


        String username = netPlayer.getUsername();
        assert (username != null);

        //TODO: Username deve essere univoco nel server e non solo nella lobby
        //Checks if the username of the player is already taken
        if(playerUsernames.contains(username)){
            response = new GenericResponse(false, "Username already taken!");
            netPlayer.getClientHandler().sendMessage(response);
            return false;
        }

        //Adding the player to the lobby
        playerUsernames.add(username);
        VirtualView view = new VirtualView(netPlayer.getClientHandler());
        netPlayer.setVirtualView(view);
        networkMap.put(username, netPlayer);
        return true;
    }

    /**
     * Broadcasts a message to all the players in the lobby (same as broadcast() in GameController)
     * @param message message to send
     */
    public void sendLobbyMessage(Message message){
        for(NetworkPlayer player: networkMap.values()){
            player.getClientHandler().sendMessage(message);
        }
    }

    /**
     * Creates and initializes all the data structures needed for the creation of an actual new game. The game has now
     * officially started.
     */
    public void startGame(){

        //Checking if the number of the players is legal before initializing the game
        assert (playerUsernames.size()>1 && playerUsernames.size()<=4);

        HashMap<String, VirtualView> viewHashMap = new HashMap<>();
        for (NetworkPlayer netPlayer: networkMap.values()) viewHashMap.put(netPlayer.getUsername(), netPlayer.getVirtualView());

        gameController = new GameController(new Game(playerUsernames), viewHashMap);
        inGame = true;
        sendLobbyMessage(new GenericResponse(true,  "Now in game!"));

    }

    /**
     * Forwards an external message to the GameController
     * @param msg message to forwards
     */
    public void sendToController(Message msg){
        gameController.messageHandler(msg);
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public ArrayList<String> getPlayerUsernames(){
        return playerUsernames;
    }
    //endregion

}
