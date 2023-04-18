package it.polimi.ingsw.server.messages;

public class NicknameMessage implements Message{

    //NOTE: used in LOGIN
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
}
