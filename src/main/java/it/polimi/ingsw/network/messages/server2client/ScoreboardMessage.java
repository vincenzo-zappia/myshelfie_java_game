package it.polimi.ingsw.network.messages.server2client;

import com.sun.source.tree.Tree;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Message containing the final scoreboard of the game
 */
public class ScoreboardMessage extends Message {
    private final TreeMap<String, Integer> scoreboard;

    public ScoreboardMessage(TreeMap<String, Integer> scoreboard) {
        super("server", MessageType.SCOREBOARD);
        this.scoreboard = scoreboard;
    }

    public TreeMap<String, Integer> getScoreboard(){
        return scoreboard;
    }

}
