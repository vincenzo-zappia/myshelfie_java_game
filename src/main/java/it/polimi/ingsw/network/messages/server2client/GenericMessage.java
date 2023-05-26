package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing a generic description
 */
public class GenericMessage extends Message {

    public GenericMessage(MessageType type){
        super("server", type);
    }

    public GenericMessage(String content, MessageType type) {
        super("server", type);
        setContent(content);
    }

}
