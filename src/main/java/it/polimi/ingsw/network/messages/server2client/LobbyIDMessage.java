package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the ID of a newly created lobby
 */
public class LobbyIDMessage extends Message {
    private final int lobbyID;

    public LobbyIDMessage(int lobbyID) {
        super("server", MessageType.LOBBY_ID);
        this.lobbyID = lobbyID;
    }

    public int getLobbyID() {
        return lobbyID;
    }

}
