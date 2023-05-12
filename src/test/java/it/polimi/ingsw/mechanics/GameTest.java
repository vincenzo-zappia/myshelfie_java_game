package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        //
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