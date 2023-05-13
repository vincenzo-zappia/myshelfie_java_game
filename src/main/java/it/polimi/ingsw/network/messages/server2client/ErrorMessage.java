package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Specific message containing the description of the error occurred
 */
public class ErrorMessage extends Message {

    public ErrorMessage(String description) {
        super("server", MessageType.ERROR_MESSAGE);
        this.setContent(description);
    }

}
