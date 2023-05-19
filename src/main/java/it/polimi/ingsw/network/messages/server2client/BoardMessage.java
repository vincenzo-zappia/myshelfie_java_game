package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.entities.util.BoardTile;

/**
 * Message containing the board updated after a tile refill
 */
public class BoardMessage extends Message {
    private final BoardTile[][] boardTiles;

    public BoardMessage(BoardTile[][] boardTiles) {
        super("server", MessageType.REFILLED_BOARD);
        this.boardTiles = boardTiles;
    }

    public BoardTile[][] getBoardCells() {
        return boardTiles;
    }
}
