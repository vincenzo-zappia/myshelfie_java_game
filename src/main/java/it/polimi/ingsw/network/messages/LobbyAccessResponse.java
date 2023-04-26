package it.polimi.ingsw.network.messages;

public class LobbyAccessResponse extends Message {
    protected LobbyAccessResponse(String sender, MessageType type) {
        super(sender, type);
    }
}
