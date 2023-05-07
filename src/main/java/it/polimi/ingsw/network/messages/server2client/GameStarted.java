package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class GameStarted extends Message {
    public GameStarted() {
        super("server", MessageType.GAME_START);
    }
}
