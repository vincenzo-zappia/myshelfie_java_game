package it.polimi.ingsw.entities;

import it.polimi.ingsw.util.CardType;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.exceptions.CellGetCardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {
    static Bookshelf bookshelf;

    @BeforeEach
    void setup(){
        bookshelf = new Bookshelf();
    }

    @Test
    void cardInsertion() throws CellGetCardException, AddCardException {
        Card card = new Card("white1.png", CardType.FRAMES);

        //Because the bookshelf is empty the row to check is the last one
        assertEquals(0, bookshelf.cardsInColumn(0));
        bookshelf.addCard(0, card);
        assertEquals(1, bookshelf.cardsInColumn(0)); //Error: cannot invoke sameCard
    }

    @Test
    void checkIfFull(){
        assertFalse(bookshelf.isBookshelfFull());
        for(int i = 0; i < 5; i++){
            for(int j = 0; j<6; j++)bookshelf.addCard(i, new Card("white1.png", CardType.values()[4]));
        }
        assertEquals(5, bookshelf.cardsInRow(5));
        assertTrue(bookshelf.isBookshelfFull());
    }

}