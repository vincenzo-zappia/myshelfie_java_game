package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message that contains a specific feedback, either true or false, with its description
 */
public class SpecificResponse extends Message {
    private final boolean response;

    public SpecificResponse(boolean response, String content, MessageType type) {
        super("server", type);
        this.response = response;
        setContent(content);
    }

    public boolean getResponse() {
        return response;
    }

}
