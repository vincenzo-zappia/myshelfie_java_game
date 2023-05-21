package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.SerializableTreeMap;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the final scoreboard of the game
 */
public class ScoreboardMessage extends Message {
    private final SerializableTreeMap<String, Integer> scoreboard;

    public ScoreboardMessage(SerializableTreeMap<String, Integer> scoreboard) {
        super("server", MessageType.SCOREBOARD);
        this.scoreboard = scoreboard;
    }

    public SerializableTreeMap<String, Integer> getScoreboard(){
        return scoreboard;
    }

}
