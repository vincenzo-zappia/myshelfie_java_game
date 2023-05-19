package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message that contains a specific feedback, either true or false, with its description
 */
public class SpecificResponse extends Message {
    private final boolean response;

    /**
     * Constructor of a message containing a description
     * @param response if the feedback is positive
     * @param content description of the feedback
     * @param type specific message type
     */
    public SpecificResponse(boolean response, String content, MessageType type) {
        super("server", type);
        this.response = response;
        setContent(content);
    }

    /**
     * Constructor of a message not containing a description
     * @param response if the feedback is positive
     * @param type specific message type
     */
    public SpecificResponse(boolean response, MessageType type) {
        super("server", type);
        this.response = response;
    }

    public boolean getResponse() {
        return response;
    }

}
