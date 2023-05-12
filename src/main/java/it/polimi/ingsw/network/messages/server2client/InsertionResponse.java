package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.Cell;

public class InsertionResponse extends Message {
    private final Cell[][] bookshelf;
    private final boolean response;
    public InsertionResponse(Cell[][] bookshelf, boolean response) {
        super("server", MessageType.INSERTION_RESPONSE);
        this.bookshelf = bookshelf;
        this.response = response;
    }

    public Cell[][] getBookshelf() {
        return bookshelf;
    }

    public boolean getResponse() {
        return response;
    }
}
