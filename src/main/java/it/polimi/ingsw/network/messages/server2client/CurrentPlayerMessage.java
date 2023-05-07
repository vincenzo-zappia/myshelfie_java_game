package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class CurrentPlayerMessage extends Message {
    public CurrentPlayerMessage(String currentPlayer) {
        super("server", MessageType.CURRENT_PLAYER);
        setContent(currentPlayer);
    }
}
