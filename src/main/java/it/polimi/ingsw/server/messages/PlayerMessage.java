package it.polimi.ingsw.server.messages;

public class PlayerMessage implements Message{

    //NOTE: message used for CHAT
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
}
