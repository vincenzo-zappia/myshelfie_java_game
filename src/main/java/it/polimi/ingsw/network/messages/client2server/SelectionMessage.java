package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class SelectionMessage extends Message {
    private final int[][] coordinates;
    public SelectionMessage(String sender, int[][] coordinates) {
        super(sender, MessageType.SELECTION_MESSAGE);
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates(){
        return coordinates;
    }
}
