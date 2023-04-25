package it.polimi.ingsw.server.messages;

//TODO: Gestione implementazione Serializable
public abstract class Message {

    //region ATTRIBUTI
    private final String sender;
    private final MessageType type;
    private String content;
    private boolean isContent;
    //endregion

    //region COSTRUTTORE
    protected Message(String sender, MessageType type) {
        this.sender = sender;
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
    //endregion

}
