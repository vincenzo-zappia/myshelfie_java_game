package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.HashMap;


//TODO: Finire di implementare catena server -> client scoreboard (cambierà l'attributo struttura dati utilizzato per la scoreboard)
/**
 * Message containing the final scoreboard of the game
 */
public class ScoreboardMessage extends Message {
    private final HashMap<String, Integer> scoreboard;

    public ScoreboardMessage(HashMap<String, Integer> scoreboard) {
        super("server", MessageType.SCOREBOARD);
        this.scoreboard = scoreboard;
    }

    public HashMap<String, Integer> getScoreboard(){
        return scoreboard;
    }

}
