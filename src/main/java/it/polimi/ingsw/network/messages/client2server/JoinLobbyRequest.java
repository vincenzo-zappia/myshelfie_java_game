package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Request sent by the player to join an existing lobby
 */
public class JoinLobbyRequest extends Message {
    private final int lobbyId;

    public JoinLobbyRequest(String sender, int lobbyId) {
        super(sender, MessageType.JOIN_LOBBY_REQUEST);
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

}
