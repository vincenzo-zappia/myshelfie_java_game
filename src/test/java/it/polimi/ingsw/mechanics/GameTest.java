package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.goals.CommonGoal1;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.util.CardType;
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
    void canSelect() {
        System.out.println(game.getPlayer("G1").getUsername());
        int[][] coordinates = new int[][]{{1, 3}};
        assertTrue(game.canSelect("G2", coordinates));

        coordinates = new int[][]{{3, 8}, {4, 8}};
        assertTrue(game.canSelect("G2", coordinates));

        coordinates = new int[][]{{4, 5}, {4, 6}, {4, 7}};
        assertFalse(game.canSelect("G3", coordinates));
    }

    @Test
    void removeCardFromBoard() {

    }

    @Test
    void addCardToBookshelf() {
    }

    @Test
    void scoreCommonGoal() {

        for(int i=0; i<2; i++) {
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] tmp = new Goal[2];
        tmp[0] = new CommonGoal1();
        tmp[1] = new CommonGoal1();
        game.scoreCommonGoal("G1");
        assertTrue(game.getPlayer("G1").getScore()>0);
    }

    @Test
    void scorePrivateGoal() {
    }

    @Test
    void isPlayerBookshelfFull() {

    }
}