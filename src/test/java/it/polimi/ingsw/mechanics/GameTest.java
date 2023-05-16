package it.polimi.ingsw.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp(){
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("G1");
        tmp.add("G2");
        tmp.add("G3");
        tmp.add("G4");

        game = new Game(tmp);
    }

    @Test
    void orderByScore(){
        game.getPlayer("G1").addScore(10);
        game.getPlayer("G2").addScore(99);
        game.getPlayer("G3").addScore(9);
        game.getPlayer("G4").addScore(12);

        TreeMap<String, Integer> res = game.orderByScore();

        System.out.println(res);

        assertEquals(res.get("G1"), 10);
        assertEquals(res.get("G2"), 99);
        assertEquals(res.get("G3"), 9);
        assertEquals(res.get("G4"), 12);
    }

    @Test
    void isSelectable() {
        int[][] coordinates = new int[][]{{0, 3}, {0, 4}};
        assertTrue(game.canSelect("pippo", coordinates));

        coordinates = new int[][]{{3, 8}, {4, 8}};
        assertTrue(game.canSelect("pippo", coordinates));

        coordinates = new int[][]{{4, 5}, {4, 6}, {4, 7}};
        assertFalse(game.canSelect("pippo", coordinates));
    }

    @Test
    void removeCardFromBoard() {

    }

    @Test
    void addCardToBookshelf() {
    }

    @Test
    void scoreCommonGoal() {
    }

    @Test
    void scorePrivateGoal() {
    }

    @Test
    void isPlayerBookshelfFull() {
    }
}