package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.Scoreboard;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the final scoreboard of the game
 */
public class ScoreboardMessage extends Message {
    private final Scoreboard scoreboard;

    public ScoreboardMessage(Scoreboard scoreboard) {
        super("server", MessageType.SCOREBOARD);
        this.scoreboard = scoreboard;
    }

    public Scoreboard getScoreboard(){
        return scoreboard;
    }

}
