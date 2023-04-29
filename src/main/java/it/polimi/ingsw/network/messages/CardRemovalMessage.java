package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Card;

public class CardRemovalMessage extends Message{
    private int[][] coordinates;
    public CardRemovalMessage(String sender, MessageType type, int[][] coordinates) {
        super(sender, type);
        this.coordinates = coordinates;
    }
}
