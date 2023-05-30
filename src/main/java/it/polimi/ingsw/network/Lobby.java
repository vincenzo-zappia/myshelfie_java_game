package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.mechanics.VirtualView;
import it.polimi.ingsw.network.messages.ChatMessage;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.server2client.GenericMessage;
import it.polimi.ingsw.network.messages.server2client.SpecificResponse;
import it.polimi.ingsw.network.messages.server2client.TextResponse;

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
    private boolean lobbyOnline;
    private boolean inGame;
    //endregion

    //region CONSTRUCTOR
    public Lobby(Server server, int lobbyID) {
        lobbyOnline = true;
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
    public boolean startGame(){

        //Checking if the number of the players is legal before initializing the game
        if(!(usernameList.size() > 1 && usernameList.size() <= 4)) {
            lobbyBroadcastMessage(new TextResponse(false, "Not enough players!"));
            lobbyBroadcastMessage(new SpecificResponse(false, MessageType.START_GAME_RESPONSE));
            return false;
        }

        //Officially starting the game
        lobbyBroadcastMessage(new SpecificResponse(true, MessageType.START_GAME_RESPONSE));
        lobbyBroadcastMessage(new TextResponse(true, "Now in game!"));
        inGame = true;

        //Creating GameController and the hashmap <PlayerUsername, VirtualView> through which GameController will manage the sending of messages from server to client
        HashMap<String, VirtualView> viewHashMap = new HashMap<>();
        for(NetworkPlayer netPlayer: networkMap.values()) viewHashMap.put(netPlayer.getUsername(), netPlayer.getVirtualView());
        gameController = new GameController(this, new Game(usernameList), viewHashMap);
        System.out.println("INFO: Game started");
        return true;
    }

    /**
     * Forwards an external message to the GameController if it's not a chat message
     * @param message message to forwards
     */
    public void sendToGame(Message message){
        if(message.getType().equals(MessageType.CHAT)) {
            ChatMessage chatMessage = (ChatMessage) message;
            if(chatMessage.getContent().charAt(0) == '@'){
                String recipient = chatMessage.getContent().split(" ")[0].substring(1);
                if(usernameList.contains(recipient)) {
                    networkMap.get(recipient).getClientHandler().sendMessage(new ChatMessage("server", message.getSender() + ": " + message.getContent()));
                    networkMap.get(chatMessage.getSender()).getClientHandler().sendMessage(new ChatMessage("server", message.getSender() + ": " + message.getContent()));
                }
            }
            else {
                lobbyBroadcastMessage(new ChatMessage("server", message.getSender() + ": " + message.getContent()));
            }
        }
        else gameController.messageHandler(message);
    }

    /**
     * Sets the lobby offline and removes it from the server list after a game has ended or a player is disconnected
     */
    public void endGame(){
        lobbyOnline = false;
        server.removeLobby(lobbyID);
    }

    /**
     * Forces all the players in the same lobby of the disconnected player to quit the game and choose between quitting or playing again
     * @param clientHandler of the disconnected player
     */
    public void forceDisconnection(ClientHandler clientHandler){
        networkMap.entrySet().stream().filter(entry -> !entry.getValue().getClientHandler().equals(clientHandler)).forEach(entry -> entry.getValue().getClientHandler().sendMessage(new GenericMessage(MessageType.DISCONNECTION)));
    }
    //endregion

    //region GETTER
    public int getLobbyID() {
        return lobbyID;
    }

    public ArrayList<String> getUsernameList(){
        return usernameList;
    }

    public boolean isLobbyOnline() {
        return lobbyOnline;
    }
    //endregion

}
