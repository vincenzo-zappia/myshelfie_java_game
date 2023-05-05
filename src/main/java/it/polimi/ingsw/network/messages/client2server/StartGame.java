package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class StartGame extends Message {
    public StartGame(String sender) {
        super(sender, MessageType.START_GAME);
    }
}
