package it.polimi.ingsw.network.messages;

public class ResponseMessage extends Message{
    private final boolean response;
    public ResponseMessage(boolean response) {
        super("server", MessageType.RESPONSE);
        this.response = response;
    }
}
