package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.Tile;

/**
 * Message containing the bookshelf updated after an insertion
 */
public class BookshelfMessage extends Message {
    private final Tile[][] bookshelf;

    public BookshelfMessage(Tile[][] bookshelf) {
        super("server", MessageType.UPDATED_BOOKSHELF);
        this.bookshelf = bookshelf;
    }

    public Tile[][] getBookshelf() {
        return bookshelf;
    }

}
