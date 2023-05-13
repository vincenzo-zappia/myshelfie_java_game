package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

//TODO: Separare il booleano dal lobby ID?
/**
 * Message containing the ID of a newly created lobby
 */
public class LobbyCreationMessage extends Message {
    private final int lobbyId;
    private final boolean successful;

    public LobbyCreationMessage(int lobbyId, boolean successful) {
        super("server", MessageType.CREATED_LOBBY);
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
