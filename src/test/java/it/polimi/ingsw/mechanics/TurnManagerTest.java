package it.polimi.ingsw.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    TurnManager turnManager;
    @BeforeEach
    void setup(){
        ArrayList<String> usernameList = new ArrayList<>(Arrays.asList("Qui", "Quo", "Qua"));
        turnManager = new TurnManager(usernameList);
    }

    @Test
    void standardNextTurn() {
        assertEquals("Qui", turnManager.getCurrentPlayer());
        assertTrue(turnManager.nextTurn());
        assertEquals("Quo", turnManager.getCurrentPlayer());
        assertTrue(turnManager.nextTurn());
        assertEquals("Qua", turnManager.getCurrentPlayer());
        assertTrue(turnManager.nextTurn());
        assertEquals("Qui", turnManager.getCurrentPlayer());
    }

    @Test
    void endGameNextTurn(){
        assertEquals("Qui", turnManager.getCurrentPlayer());
        turnManager.startEndGame();
        assertTrue(turnManager.inEndGame());
        assertTrue(turnManager.nextTurn());
        assertEquals("Quo", turnManager.getCurrentPlayer());
        assertTrue(turnManager.nextTurn());
        assertEquals("Qua", turnManager.getCurrentPlayer());
        assertFalse(turnManager.nextTurn());
    }

}