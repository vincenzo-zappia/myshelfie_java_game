package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class SelectionMessage extends Message {
    private int[][] coordinates;
    protected SelectionMessage(String sender) {
        super(sender, MessageType.SELECTION_MESSAGE);
    }

    public int[][] getCoordinates(){
        return coordinates;
    }
}
