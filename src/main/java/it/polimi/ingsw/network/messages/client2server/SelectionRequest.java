package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Request sent by the player to make a card selection
 */
public class SelectionRequest extends Message {
    private final int[][] coordinates;

    public SelectionRequest(String sender, int[][] coordinates) {
        super(sender, MessageType.SELECTION_REQUEST);
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates(){
        return coordinates;
    }

}
