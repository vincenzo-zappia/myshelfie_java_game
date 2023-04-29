package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Card;

public class CardRemovalMessage extends Message{
    private final int[][] coordinates;
    public CardRemovalMessage(int[][] coordinates) {
        super("server", MessageType.CARD_REMOVAL);
        this.coordinates = coordinates;
    }
}
