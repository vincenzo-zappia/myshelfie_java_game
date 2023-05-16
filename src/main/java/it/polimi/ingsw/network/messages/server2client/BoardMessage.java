package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;

/**
 * Message containing the board updated after a tile refill
 */
public class BoardMessage extends Message {
    private final BoardCell[][] boardCells;

    public BoardMessage(BoardCell[][] boardCells) {
        super("server", MessageType.REFILLED_BOARD);
        this.boardCells = boardCells;
    }

    public BoardCell[][] getBoardCells() {
        return boardCells;
    }
}
