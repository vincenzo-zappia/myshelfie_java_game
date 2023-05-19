package it.polimi.ingsw.network.messages;

import java.io.Serializable;

/**
 * General layout of a message
 */
public abstract class Message implements Serializable {
    private final String sender;
    private final MessageType type;
    private String content;

    protected Message(String sender, MessageType type) {
        this.sender = sender;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }
    public MessageType getType() {
        return type;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

}
