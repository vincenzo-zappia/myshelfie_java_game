package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.Cell;

/**
 * Message containing the bookshelf updated after an insertion
 */
public class BookshelfMessage extends Message {
    private final Cell[][] bookshelf;

    public BookshelfMessage(Cell[][] bookshelf) {
        super("server", MessageType.BOOKSHELF_UPDATE);
        this.bookshelf = bookshelf;
    }

    public Cell[][] getBookshelf() {
        return bookshelf;
    }

}
