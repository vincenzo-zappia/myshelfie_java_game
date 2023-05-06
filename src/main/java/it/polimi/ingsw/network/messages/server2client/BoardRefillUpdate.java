package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;

public class BoardRefillUpdate extends Message {
    private final BoardCell[][] boardCells;
    public BoardRefillUpdate(BoardCell[][] boardCells) {
        super("server", MessageType.BOARD_REFILL);
        this.boardCells = boardCells;
    }

    public BoardCell[][] getBoardCells() {
        return boardCells;
    }
}
