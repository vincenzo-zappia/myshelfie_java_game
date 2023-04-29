package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Board;

public class BoardRefillUpdate extends Message{
    private final Board board;
    public BoardRefillUpdate(Board board) {
        super("server", MessageType.BOARD_REFILL);
        this.board = board;
    }
}
