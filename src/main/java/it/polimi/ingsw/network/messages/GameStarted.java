package it.polimi.ingsw.network.messages;

public class GameStarted extends Message {
    public GameStarted(String sender, MessageType type) {
        super(sender, type);
    }
}
