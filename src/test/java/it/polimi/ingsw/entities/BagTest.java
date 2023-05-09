package it.polimi.ingsw.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {
    private Bag bag;
    @BeforeEach
    void setUp() {
        bag = new Bag();
    }

    @Test
    void isBagEmpty() {
        for(int i = 0; i<132;i++)bag.drawCard();
        assertTrue(bag.isBagEmpty());
    }

    @Test
    void drawCard() {
        for(int i = 0; i<132;i++) assertNotNull(bag.drawCard());
    }
}