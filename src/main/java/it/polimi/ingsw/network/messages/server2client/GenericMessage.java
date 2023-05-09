package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class GenericMessage extends Message {
    public GenericMessage(MessageType type, String content) {
        super("server", type);
        setContent(content);
    }
}
