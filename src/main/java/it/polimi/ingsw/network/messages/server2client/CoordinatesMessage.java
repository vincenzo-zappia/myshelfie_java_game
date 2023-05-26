package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the coordinates of cards that were either checked or removed from the board
 */
public class CoordinatesMessage extends Message {
    private final int[][] coordinates;
    private boolean valid;

    public CoordinatesMessage(boolean valid, int[][] coordinates, MessageType type){
        super("server", type);
        this.valid = valid;
        this.coordinates = coordinates;
    }

    public CoordinatesMessage(int[][] coordinates, MessageType type) {
        super("server", type);
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public boolean isValid() {
        return valid;
    }
}
