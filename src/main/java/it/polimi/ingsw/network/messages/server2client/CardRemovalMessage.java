package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * ordina ai client di eliminare le carte selezionate (server -> client)
 */
public class CardRemovalMessage extends Message {
    private final int[][] coordinates;
    public CardRemovalMessage(int[][] coordinates) {
        super("server", MessageType.CARD_REMOVAL);
        this.coordinates = coordinates;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }
}
