package it.polimi.ingsw;

import it.polimi.ingsw.server.model.entities.Player;
import it.polimi.ingsw.server.model.state.Game;

import java.util.ArrayList;
//IMPORTANTE: forse si può eliminare questa classe!
public class Lobby {
    private final ArrayList<Player> playerList;

    public Lobby(){
        playerList = new ArrayList<Player>();
    }
    public void addToQueue(Player p){
        playerList.add(p);
    }
    public Game startMatch(){return new Game(playerList.size());}
}
