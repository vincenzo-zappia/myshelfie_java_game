package it.polimi.ingsw.network.messages;

public class GameStart extends Message {
    protected GameStart(String sender, MessageType type) {
        super(sender, type);
    }
}
