package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Player;
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
        tmp.add("NotoriusBig");

        game = new Game(tmp);
    }

    @Test
    void orderByScore(){
        game.getPlayers().get(0).addScore(300);
        game.getPlayers().get(1).addScore(139);
        game.getPlayers().get(2).addScore(1000);
        game.getPlayers().get(3).addScore(890);

        HashMap<Integer, String> res = game.orderByScore();

       // for(int i = 0; i< 4;i++) System.out.println(game.getPlayers().get(i).getUsername());

        assertEquals(res.get(1), "MassimoTroisi");
        assertEquals(res.get(2), "NotoriusBig");
        assertEquals(res.get(3), "Gemitaiz");
        assertEquals(res.get(4), "Francesco");
    }

    @Test
    void isSelectable() {
        int[][] coordinates = new int[][]{{0, 3}, {0, 4}};
        assertTrue(game.isSelectable(coordinates));

        coordinates = new int[][]{{3, 8}, {4, 8}};
        assertTrue(game.isSelectable(coordinates));

        coordinates = new int[][]{{4, 5}, {4, 6}, {4, 7}};
        assertFalse(game.isSelectable(coordinates));
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