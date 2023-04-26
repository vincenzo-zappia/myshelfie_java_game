package it.polimi.ingsw.network.messages;

public class SelectionMessage extends Message{
    private int[][] coordinates;
    protected SelectionMessage(String sender, MessageType type) {
        super(sender, type);
    }

    public int[][] getCoordinates(){
        return coordinates;
    }
}
