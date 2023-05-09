package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.HashMap;

public class ScoreboardMessage extends Message {
    private final HashMap<String, Integer> scoreboard;

    public ScoreboardMessage(HashMap<String, Integer> scoreboard) {
        super("server", MessageType.SCOREBOARD);
        this.scoreboard = scoreboard;
    }
}
