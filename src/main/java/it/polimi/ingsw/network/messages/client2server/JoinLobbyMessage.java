package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class JoinLobbyMessage extends Message {
    private final int lobbyId;

    public JoinLobbyMessage(String sender, int lobbyId) {
        super(sender, MessageType.JOIN_LOBBY);
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }
}
