package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class CoordinatesCheckMessage extends Message {
    private int[][] coordinates;
    public CoordinatesCheckMessage(int[][] coordinates) {
        super("server", MessageType.COORDINATES_CHECK);
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }
}
