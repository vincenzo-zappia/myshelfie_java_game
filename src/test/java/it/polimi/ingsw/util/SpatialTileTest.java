package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpatialTileTest {
    SpatialTile spatialTile;
    @BeforeEach
    void setUp() {
        spatialTile = new SpatialTile(0,0, CardType.FRAMES);
    }

    @Test
    void getCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        assertTrue(x.sameType(spatialTile.getCard()));
    }

    @Test
    void setCard() {
        Card x = new Card("img.png", CardType.FRAMES);
        spatialTile.setCard(x);
        assertEquals(x, spatialTile.getCard());
    }

    @Test
    void setCellEmpty() {
        spatialTile.setTileEmpty();
        assertTrue(spatialTile.isTileEmpty());
    }

    @Test
    void isCellEmpty() {
        assertFalse(spatialTile.isTileEmpty());
    }

    @Test
    void getColumn() {
        assertEquals(0, spatialTile.getColumn());
    }

    @Test
    void getRow() {
        assertEquals(0, spatialTile.getRow());
    }
}