package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the ID of a newly created lobby
 */
public class LobbyIDMessage extends Message {
    private final int lobbyId;

    public LobbyIDMessage(int lobbyId) {
        super("server", MessageType.CREATED_LOBBY);
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }


}
