package it.polimi.ingsw.network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    //region ATTRIBUTES
    private final String username; //Sender. Can be either server or a player
    private final MessageType type;
    private String content;
    private boolean hasContent;
    //endregion

    //region CONSTRUCTOR
    protected Message(String sender, MessageType type) {
        this.username = sender;
        this.type = type;
        this.hasContent = false;
    }
    //endregion

    //region METHODS
    public boolean hasContent() {
        return hasContent;
    }

    public void setContent(String content) {
        this.content = content;
        this.hasContent = true;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }
    //endregion

}
