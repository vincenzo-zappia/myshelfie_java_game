package it.polimi.ingsw.mechanics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp(){
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("Gemitaiz");
        tmp.add("Francesco");
        tmp.add("MassimoTroisi");
        tmp.add("NotoriousBig");

        game = new Game(tmp);
    }

    @Test
    void getScoreboard(){
        game.getPlayers().get(0).addScore(60);
        game.getPlayers().get(1).addScore(31);
        game.getPlayers().get(2).addScore(100);
        game.getPlayers().get(3).addScore(95);

        for(int i = 0; i< 4;i++) System.out.println(game.getPlayers().get(i).getUsername() + " " + game.getPlayers().get(i).getScore());

        HashMap<String, Integer> res = game.getScoreboard();

        System.out.println(res);

        assertEquals(res.get("MassimoTroisi"), 100);
        assertEquals(res.get("Gemitaiz"), 95);
        assertEquals(res.get("Francesco"), 60);
        assertEquals(res.get("NotoriousBig"), 31);

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