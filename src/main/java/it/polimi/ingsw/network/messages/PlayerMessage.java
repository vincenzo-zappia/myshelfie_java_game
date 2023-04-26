package it.polimi.ingsw.network.messages;

public class PlayerMessage extends Message{
    protected PlayerMessage(String sender, MessageType type) {
        super(sender, type);
    }

    //NOTE: message used for CHAT
    /*
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
     */
}
