package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTileTest {

    BoardTile boardTile;
    @BeforeEach
    void setUp() {
        boardTile = new BoardTile();
    }

    @Test
    void getCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        boardTile.setCard(x);
        assertEquals(x, boardTile.getCard());
    }

    @Test
    void setCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        boardTile.setCard(x);
        assertEquals(x, boardTile.getCard());
    }

    @Test
    void setCellEmpty() {
        boardTile.setTileEmpty();
        assertTrue(boardTile.isTileEmpty());
    }

    @Test
    void isCellEmpty() {
        assertTrue(boardTile.isTileEmpty());
        boardTile.setCard(new Card("img.png", CardType.FRAMES));
        assertFalse(boardTile.isTileEmpty());
    }

    @Test
    void setCellActive() {
        assertFalse(boardTile.isTileActive());
        boardTile.setTileActive();
        assertTrue(boardTile.isTileActive());
    }

    @Test
    void isCellActive() {
        assertFalse(boardTile.isTileActive());
        boardTile.setTileActive();
        assertTrue(boardTile.isTileActive());
    }
}