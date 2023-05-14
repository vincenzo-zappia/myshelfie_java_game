package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

/**
 * Request sent by the player with the column where to insert the selected cards
 */
public class InsertionRequest extends Message {
    private final int selectedColumn;

    public InsertionRequest(String sender, int selectedColumn) {
        super(sender, MessageType.INSERTION_REQUEST);
        this.selectedColumn = selectedColumn;
    }

    public int getSelectedColumn() {
        return selectedColumn;
    }

}
