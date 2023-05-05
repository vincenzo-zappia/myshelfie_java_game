package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class BoardRefillUpdate extends Message {
    private final Board board;
    public BoardRefillUpdate(Board board) {
        super("server", MessageType.BOARD_REFILL);
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
