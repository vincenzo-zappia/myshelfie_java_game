package it.polimi.ingsw.network.messages;

public class JoinLobby extends Message {
    private final int lobbyId;

    public JoinLobby(String sender, MessageType type, int lobbyId) {
        super(sender, type);
        this.lobbyId = lobbyId;
    }

    public int getLobbyId() {
        return lobbyId;
    }
}
