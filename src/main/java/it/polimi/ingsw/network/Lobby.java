package it.polimi.ingsw.network;

import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.mechanics.GameController;

import java.util.ArrayList;
//IMPORTANTE: forse si può eliminare questa classe!
//TODO: classe che crea effettivamente la partita: crea istanza di Game, crea i CommonGoal da associare a Game, associa Game a GameController
//TODO: e lega i Client ai corrispettivi Player e username
public class Lobby {
    private final Server server;
    private final ArrayList<Client> clients;
    private final ArrayList<String> playerList;
    private int playerNum;

    private GameController gameController;

    public Lobby(Server server, ArrayList<Client> clients, ArrayList<String> playerUsernames) {
        this.server = server;
        this.clients = clients;
        this.playerList = playerUsernames;
    }
    public void addToQueue(String username){
        playerList.add(username);
    }
    public Game startMatch(){return new Game(playerList.size());}
}
