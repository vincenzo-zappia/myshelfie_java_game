package it.polimi.ingsw.server.messages;

public class MoveMessage implements Message{
    //NOTE: used to send/receive moves of the players
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
}
