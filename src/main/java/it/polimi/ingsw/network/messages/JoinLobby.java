package it.polimi.ingsw.network.messages;

public class JoinLobby extends Message {
    protected JoinLobby(String sender, MessageType type) {
        super(sender, type);
    }
}
