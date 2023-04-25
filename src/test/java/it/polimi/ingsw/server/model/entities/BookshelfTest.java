package it.polimi.ingsw.server.model.entities;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.exceptions.CellGetCardException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {
    static Bookshelf bookshelf;

    @BeforeAll
    static void setup(){
        bookshelf = new Bookshelf();
    }

    @Test
    void cardInsertion() throws CellGetCardException, AddCardException {
        //TODO: review when implemented enumerations
        Card card = new Card("white1.png", TileType.values()[4]);

        //Because the bookshelf is empty the row to check is the last one
        assertTrue(bookshelf.getCell(5, 4).isCellEmpty());
        bookshelf.addCard(4, card);
        assertTrue(bookshelf.getCell(5, 4).getCard().sameCard(card)); //Error: cannot invoke sameCard
    }

}