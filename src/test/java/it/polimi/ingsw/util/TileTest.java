package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile tile;
    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    @Test
    void getCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        tile.setCard(x);
        assertEquals(x, tile.getCard());
    }

    @Test
    void setCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        tile.setCard(x);
        assertEquals(x, tile.getCard());
    }

    @Test
    void setCellEmpty() {
        tile.setTileEmpty();
        assertTrue(tile.isTileEmpty());
    }

    @Test
    void isCellEmpty() {
        assertTrue(tile.isTileEmpty());
        tile.setCard(new Card("img.png", CardType.FRAMES));
        assertFalse(tile.isTileEmpty());
    }
}