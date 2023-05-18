package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardCellTest {

    BoardCell boardCell;
    @BeforeEach
    void setUp() {
        boardCell = new BoardCell();
    }

    @Test
    void getCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        boardCell.setCard(x);
        assertEquals(x, boardCell.getCard());
    }

    @Test
    void setCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        boardCell.setCard(x);
        assertEquals(x, boardCell.getCard());
    }

    @Test
    void setCellEmpty() {
        boardCell.setCellEmpty();
        assertTrue(boardCell.isCellEmpty());
    }

    @Test
    void isCellEmpty() {
        assertTrue(boardCell.isCellEmpty());
        boardCell.setCard(new Card("img.png", CardType.FRAMES));
        assertFalse(boardCell.isCellEmpty());
    }

    @Test
    void setCellActive() {
        assertFalse(boardCell.isCellActive());
        boardCell.setCellActive();
        assertTrue(boardCell.isCellActive());
    }

    @Test
    void isCellActive() {
        assertFalse(boardCell.isCellActive());
        boardCell.setCellActive();
        assertTrue(boardCell.isCellActive());
    }
}