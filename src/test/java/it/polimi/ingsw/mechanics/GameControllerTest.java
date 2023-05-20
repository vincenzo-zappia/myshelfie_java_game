package it.polimi.ingsw.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private Game game;
    private HashMap<String, VirtualView> viewHashMap;
    private GameController gameController;

    @BeforeEach
    void setUp() {
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("G1");
        tmp.add("G2");
        tmp.add("G3");
        tmp.add("G4");

        game = new Game(tmp);
        gameController = new GameController(game, viewHashMap);
    }

    @Test
    void messageHandler() {
    }

    @Test
    void broadcastMessage() {
    }

    @Test
    void cardSelection() {
    }

    @Test
    void cardInsertion() {
    }

    @Test
    void findWinner() {
    }
}