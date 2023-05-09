package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class StartGameResponse extends Message {
    public StartGameResponse() {
        super("server", MessageType.START_GAME_RESPONSE);
    }
}
