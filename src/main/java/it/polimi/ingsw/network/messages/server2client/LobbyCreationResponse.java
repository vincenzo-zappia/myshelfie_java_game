package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class LobbyCreationResponse extends Message {
    private final int lobbyId;
    private final boolean successful;
    public LobbyCreationResponse(int lobbyId, boolean successful) {
        super("server", MessageType.LOBBY_CREATION_RESPONSE);
        this.lobbyId = lobbyId;
        this.successful = successful;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
