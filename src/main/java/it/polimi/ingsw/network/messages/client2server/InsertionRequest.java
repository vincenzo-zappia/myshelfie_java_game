package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

/**
 * Request sent by the player to insert the selected cards
 */
public class InsertionRequest extends Message {
    private final ArrayList<Card> selectedCards;
    private final int selectedColumn;

    public InsertionRequest(String sender, ArrayList<Card> selectedCards, int selectedColumn) {
        super(sender, MessageType.INSERTION_REQUEST);
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
