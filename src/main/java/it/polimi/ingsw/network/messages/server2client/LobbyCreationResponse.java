package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class LobbyCreationResponse extends Message {
    private final int lobbyId;
    public LobbyCreationResponse(int lobbyId) {
        super("server", MessageType.LOBBY_CREATION_RESPONSE);
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }
}
