package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Board;

public class BoardRefillMessage extends Message{
    private final Board board;
    public BoardRefillMessage(Board board) {
        super("server", MessageType.BOARD_REFILL);
        this.board = board;
    }
}
