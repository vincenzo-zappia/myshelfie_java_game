package it.polimi.ingsw.server.messages;

public class MoveMessage extends Message{
    protected MoveMessage(String sender, MessageType type) {
        super(sender, type);
    }
    //NOTE: used to send/receive moves of the players
    /*
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
     */
}
