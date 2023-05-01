package it.polimi.ingsw.network.messages;


public class ErrorMessage extends Message{
    private final String description;
    public ErrorMessage(String sender, MessageType type, String description) {
        super(sender, type);
        this.description = "ERROR: "+description;
    }

    public String getDescription() {
        return description;
    }
}
