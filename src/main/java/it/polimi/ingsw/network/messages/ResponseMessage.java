package it.polimi.ingsw.network.messages;

public class ResponseMessage extends Message{
    private boolean response;
    public ResponseMessage(String sender, MessageType type, boolean response) {
        super(sender, type);
        this.response = response;
    }
}
