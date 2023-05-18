package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.goals.CommonGoal1;
import it.polimi.ingsw.entities.goals.CommonGoal5;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.exceptions.AddCardException;
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

    /**
     * Checks if the treeMap that represent scoreboard is effectively
     * order by descendent score
     */
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

    /**
     * Tests that cards selected by players are selectable (according to game's rules)
     */
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

    /**
     * Checks if, when a player call this method, Board return an ArrayList
     * of the selected cards
     */
    @Test
    void removeCardFromBoard() {
        Card x = game.getBoard().getCard(1,3);
        assertEquals(x, game.removeCardsFromBoard(new int[][]{{1, 3}}).get(0));
    }

    /**
     * Checks that a combination of cards is effectively added
     * to player's bookshelf
     */
    @Test
    void addCardToBookshelf() {
        ArrayList<Card> tmp = new ArrayList<>();
        tmp.add(new Card("img.png", CardType.FRAMES));
        tmp.add(new Card("img.png", CardType.FRAMES));
        tmp.add(new Card("img.png", CardType.FRAMES));

        game.addCardsToBookshelf("G1", 0, tmp);
        assertEquals(3, game.getPlayer("G1").getBookshelf().cardsInColumn(0));
    }

    /**
     * Checks that casual combination of cards in bookshelf are not recognized by checkGoal()
     * and that score isn't effectively added to player's attribute
     */
    @Test
    void scoreCommonGoalCaseFalse(){
        for(int i=0; i<2; i++) {
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.GAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] tmp = new Goal[2];
        tmp[0] = new CommonGoal1();
        tmp[1] = new CommonGoal1();

        game.setCommonGoals(tmp);
        game.scoreCommonGoal("G1");
        assertFalse(game.getPlayer("G1").getScore()>0);

        for(int i=0; i<3; i++) {
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.CATS));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.BOOKS));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.GAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.PLANTS));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] v = new Goal[2];
        v[0] = new CommonGoal5();
        v[1] = new CommonGoal5();
        game.setCommonGoals(v);
        game.scoreCommonGoal("G2");
        assertFalse(game.getPlayer("G2").getScore()>0);
    }

    /**
     * Checks that CommonGoals (1 and 5) are truly recognized by checkGoal()
     * and that score is effectively added to player's attribute
     */
    @Test
    void scoreCommonGoalCaseTrue() {
        for(int i=0; i<2; i++) {
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G1").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] tmp = new Goal[2];
        tmp[0] = new CommonGoal1();
        tmp[1] = new CommonGoal1();
        game.setCommonGoals(tmp);
        game.scoreCommonGoal("G1");
        assertTrue(game.getPlayer("G1").getScore()>0);

        for(int i=0; i<3; i++) {
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.CATS));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.BOOKS));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.GAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] v = new Goal[2];
        v[0] = new CommonGoal5();
        v[1] = new CommonGoal5();
        game.setCommonGoals(v);
        game.scoreCommonGoal("G2");
        assertTrue(game.getPlayer("G2").getScore()>0);
    }


    //TODO: da verificare
    @Test
    void scorePrivateGoal() {
        /*
        game.getPlayer("G2").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(0, new Card("img.png", CardType.PLANTS));

        game.getPlayer("G2").getBookshelf().addCard(2, new Card("img.png", CardType.TROPHIES));
        game.getPlayer("G2").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(2, new Card("img.png", CardType.PLANTS));

        game.getPlayer("G2").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(3, new Card("img.png", CardType.BOOKS));
        game.getPlayer("G2").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(3, new Card("img.png", CardType.PLANTS));

        game.getPlayer("G2").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("G2").getBookshelf().addCard(4, new Card("img.png", CardType.CATS));
        game.getPlayer("G2").getBookshelf().addCard(4, new Card("img.png", CardType.PLANTS));


        game.getPlayer("G2").setPrivateGoal(new PrivateGoal(0));

        game.scorePrivateGoal();
        assertTrue(game.getPlayer("G2").getScore()>0);

         */
    }

    /**
     * Checks that full-filled bookshelf is recognized by isBookshelfFull() method
     */
    @Test
    void isPlayerBookshelfFull() {
        for(int i=0; i<5; i++) {
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G2").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        assertTrue(game.isPlayerBookshelfFull("G2"));
    }

    /**
     * Checks that empty bookshelf isn't recognized by isBookshelfFull() method
     */
    @Test
    void isPlayerBookshelfNotFull() {
        assertFalse(game.isPlayerBookshelfFull("G2"));
    }

    /**
     * Check if player can add 1 (up to 3) cards into its bookshelf (RETURN TRUE)
     */
    @Test
    void canInsertCaseTrue(){
        assertTrue(game.canInsert("G2", 0, 3));
        for(int i = 0; i<4; i++)game.getPlayer("G2").addCardToBookshelf(0, new Card("img.png", CardType.FRAMES));
        assertTrue(game.canInsert("G2", 0, 2));
        assertTrue(game.canInsert("G2", 0, 1));
    }

    /**
     * Check if player can add 1 (up to 3) cards into its bookshelf (RETURN FALSE)
     */
    @Test
    void canInsertCaseFalse(){

        for(int i = 0; i<4; i++)game.getPlayer("G2").addCardToBookshelf(0, new Card("img.png", CardType.FRAMES));
        assertFalse(game.canInsert("G2", 0, 3));

        for(int i=0; i<5; i++) {
            game.getPlayer("G3").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G3").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G3").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G3").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G3").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("G3").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        assertFalse(game.canInsert("G3", 0, 3));
        assertFalse(game.canInsert("G3", 0, 2));
        assertFalse(game.canInsert("G3", 0, 69));
        assertFalse(game.canInsert("G3", 0, 0));
    }

    /**
     * Checks when boards need to be refilled with cards from Bag (RETURN TRUE)
     */
    @Test
    void checkRefillCaseTrue(){
        int[][] x = new int[81][2];
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                x[i][0] = i;
                x[i][1] = j;
                game.removeCardsFromBoard(x);
            }
        }
        assertTrue(game.checkRefill());
    }

    /**
     * Checks when boards need to be refilled with cards from Bag (RETURN FALSE)
     */
    @Test
    void checkRefillCaseFalse(){
        game.removeCardsFromBoard(new int[][]{{1,3}});
        assertFalse(game.checkRefill());
    }
}