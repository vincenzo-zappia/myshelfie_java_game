package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message that contains a server textual message that has to be perceived either as a negative or a positive one
 */
public class TextResponse extends Message {
    private final boolean response;

    public TextResponse(boolean response, String content) {
        super("server", MessageType.GENERIC_RESPONSE);
        this.response = response;
        setContent(content);
    }

    public boolean getResponse() {
        return response;
    }

}
