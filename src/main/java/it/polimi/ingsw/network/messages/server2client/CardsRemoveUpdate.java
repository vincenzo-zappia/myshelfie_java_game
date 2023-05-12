package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * ordina ai client di eliminare le carte selezionate (server -> client)
 */
public class CardsRemoveUpdate extends Message {
    private final int[][] coordinates;
    public CardsRemoveUpdate(int[][] coordinates) {
        super("server", MessageType.CARDS_REMOVE_UPDATE);
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }
}
