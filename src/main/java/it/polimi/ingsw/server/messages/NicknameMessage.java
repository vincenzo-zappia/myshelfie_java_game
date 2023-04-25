package it.polimi.ingsw.server.messages;

public class NicknameMessage extends Message{
    protected NicknameMessage(String sender, MessageType type) {
        super(sender, type);
    }

    //NOTE: used in LOGIN

    /*
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
     */

}
