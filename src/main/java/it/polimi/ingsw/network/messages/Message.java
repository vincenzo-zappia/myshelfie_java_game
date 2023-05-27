package it.polimi.ingsw.network.messages;

import java.io.Serializable;

/**
 * General functionalities of a message
 */
public abstract class Message implements Serializable {

    //region ATTRIBUTES
    private final String sender;
    private final MessageType type;
    private String content;
    //endregion

    //region CONSTRUCTOR
    protected Message(String sender, MessageType type) {
        this.sender = sender;
        this.type = type;
    }
    //endregion

    //region GETTER AND SETTER
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
    //endregion

}
