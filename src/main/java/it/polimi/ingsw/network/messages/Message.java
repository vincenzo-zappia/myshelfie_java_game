package it.polimi.ingsw.network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    //region ATTRIBUTI
    private final String username;
    private final MessageType type;
    private String content;
    private boolean isContent;
    //endregion

    //region COSTRUTTORE
    protected Message(String sender, MessageType type) {
        this.username = sender;
        this.type = type;
        this.isContent = false;
    }
    //endregion

    //region METHODS
    public boolean isContent() {
        return isContent;
    }

    public void setContent(String content) {
        this.content = content;
        this.isContent = true;
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
