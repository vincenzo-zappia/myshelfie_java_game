package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;
import it.polimi.ingsw.mechanics.VirtualView;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    //region ATTRIBUTES
    private final int lobbyId;
    private final Server server;
    private ArrayList<String> playerUsernames;
    private HashMap<String, NetworkPlayer> networkMap;
    private boolean inGame;
    private GameController gameController;
    //endregion

    //TODO: DA REVISIONARE (quando la lobby viene creata non si conoscono gli username di tutti i player)
    //region CONSTRUCTOR
    public Lobby(Server server, int lobbyId) {
        inGame = false;
        this.lobbyId = lobbyId;
        this.server = server;
    }
    //endregion

    //region METHOD
    //TODO: Metodo chiamato da clientHandler per aggiungere se stesso alla Lobby
    public void joinLobby(NetworkPlayer netPlayer){
        String username = netPlayer.getUsername();
        if(playerUsernames.size()>=4) return; //TODO: invio messaggio lobby piena

        assert (username != null);
        if(playerUsernames.contains(username)) return; //TODO: invio messaggio username already taken

        //Aggiunta effettiva del player
        playerUsernames.add(username);
        VirtualView view = new VirtualView(netPlayer.getClientHandler());
        netPlayer.setVirtualView(view);
        networkMap.put(username, netPlayer);
    }

    //TODO: Metodo che deve inizializzare Game e GameController (a cui deve essere passata l'hashmap)
    public Game startGame(){
        assert (playerList.size()>1 && playerList.size()<=4)

        //TODO: implementare invio messaggio di startgame (dopo verifica dei prerequisiti)

        gameController = new GameController(playerUsernames, new Game(playerUsernames), );
        inGame = true;
    }

    public void sendToController(Message msg){
        gameController.messageHandler(msg);
    }

    public int getLobbyId() {
        return lobbyId;
    }
    //endregion
}
