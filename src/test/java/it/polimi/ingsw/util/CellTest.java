package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    Cell cell;
    @BeforeEach
    void setUp() {
        cell = new Cell();
    }

    @Test
    void getCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        cell.setCard(x);
        assertEquals(x, cell.getCard());
    }

    @Test
    void setCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        cell.setCard(x);
        assertEquals(x, cell.getCard());
    }

    @Test
    void setCellEmpty() {
        cell.setCellEmpty();
        assertTrue(cell.isCellEmpty());
    }

    @Test
    void isCellEmpty() {
        assertTrue(cell.isCellEmpty());
        cell.setCard(new Card("img.png", CardType.FRAMES));
        assertFalse(cell.isCellEmpty());
    }
}