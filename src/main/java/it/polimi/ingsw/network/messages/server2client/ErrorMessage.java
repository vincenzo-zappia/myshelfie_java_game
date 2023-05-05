package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Generic message on par with ResponseMessage used to pass specific error content
 */
public class ErrorMessage extends Message {

    public ErrorMessage(String description) {
        super("server", MessageType.ERROR_MESSAGE);
        this.setContent(description);
    }

}
