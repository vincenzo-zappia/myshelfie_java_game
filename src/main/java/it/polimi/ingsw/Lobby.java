package proj.polimi.testrj45;

import proj.polimi.testrj45.entities.Player;
import proj.polimi.testrj45.state.Game;

import java.util.ArrayList;
//IMPORTANTE: forse si può eliminare questa classe!
public class Lobby {
    private ArrayList<Player> playerList;

    public Lobby(){
        playerList = new ArrayList<Player>();
    }
    public void addToQueue(Player p){
        playerList.add(p);
    }
    public Game startMatch(){return new Game(playerList.size());}
}
