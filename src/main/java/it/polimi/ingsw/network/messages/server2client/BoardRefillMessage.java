package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;

public class BoardRefillMessage extends Message {
    private final BoardCell[][] boardCells;
    public BoardRefillMessage(BoardCell[][] boardCells) {
        super("server", MessageType.BOARD_REFILL);
        this.boardCells = boardCells;
    }

    public BoardCell[][] getBoardCells() {
        return boardCells;
    }
}
