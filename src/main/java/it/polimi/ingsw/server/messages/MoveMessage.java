package it.polimi.ingsw.server.messages;

import it.polimi.ingsw.server.model.entities.Card;

import java.util.ArrayList;

public class MoveMessage extends Message{
    private ArrayList<Card> selectedCards;
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
