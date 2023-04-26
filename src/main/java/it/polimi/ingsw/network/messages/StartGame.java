package it.polimi.ingsw.network.messages;

public class StartGame extends Message {
    protected StartGame(String sender, MessageType type) {
        super(sender, type);
    }
}
