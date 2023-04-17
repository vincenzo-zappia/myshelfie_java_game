package it.polimi.ingsw.server;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.server.model.mechanics.GameController;

import java.util.ArrayList;

//TODO: classe che crea effettivamente la partita: crea istanza di Game, crea i CommonGoal da associare a Game, associa Game a GameController
//TODO: e lega i Client ai corrispettivi Player e username
public class Lobby {
    private final Server server;
    private final ArrayList<Client> clients;
    private final ArrayList<String> playerUsernames;
    private int playerNum;

    private GameController gameController;

    public Lobby(Server server, ArrayList<Client> clients, ArrayList<String> playerUsernames) {
        this.server = server;
        this.clients = clients;
        this.playerUsernames = playerUsernames;
    }
}
