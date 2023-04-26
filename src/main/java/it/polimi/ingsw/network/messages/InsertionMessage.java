package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Card;

import java.util.ArrayList;

public class InsertionMessage extends Message{
    private ArrayList<Card> selectedCards;
    private int selectedColumn;

    protected InsertionMessage(String sender, MessageType type) {
        super(sender, type);
    }


    public ArrayList<Card> getSelectedCards() {
        return selectedCards;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }
}
