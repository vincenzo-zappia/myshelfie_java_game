package it.polimi.ingsw.network.messages;

public class LobbyAccessResponse extends Message {

    //region ATTRIBUTES
    private final boolean successful;
    //endregion

    public LobbyAccessResponse(String sender, MessageType type, boolean successful) {
        super(sender, type);
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
