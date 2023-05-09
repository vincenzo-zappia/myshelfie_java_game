package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class StartGameRequest extends Message {
    public StartGameRequest(String sender) {
        super(sender, MessageType.START_GAME_REQUEST);
    }
}
