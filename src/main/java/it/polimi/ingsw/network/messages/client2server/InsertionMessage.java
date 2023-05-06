package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

public class InsertionMessage extends Message {
    private final ArrayList<Card> selectedCards;
    private final int selectedColumn;

    public InsertionMessage(String sender, ArrayList<Card> selectedCards, int selectedColumn) {
        super(sender, MessageType.INSERTION_MESSAGE);
        this.selectedCards = selectedCards;
        this.selectedColumn = selectedColumn;
    }


    public ArrayList<Card> getSelectedCards() {
        return selectedCards;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }
}
