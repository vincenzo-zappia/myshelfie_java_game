package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecialCellTest {
    SpecialCell specialCell;
    @BeforeEach
    void setUp() {
        specialCell = new SpecialCell(0,0, CardType.FRAMES);
    }

    @Test
    void getCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        assertTrue(x.sameType(specialCell.getCard()));
    }

    @Test
    void setCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        specialCell.setCard(x);
        assertEquals(x, specialCell.getCard());
    }

    @Test
    void setCellEmpty() {
        specialCell.setCellEmpty();
        assertTrue(specialCell.isCellEmpty());
    }

    @Test
    void isCellEmpty() {
        assertFalse(specialCell.isCellEmpty());
    }

    @Test
    void getColumn() {
        assertEquals(0, specialCell.getColumn());
    }

    @Test
    void getRow() {
        assertEquals(0, specialCell.getRow());
    }
}