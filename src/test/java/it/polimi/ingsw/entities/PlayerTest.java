package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.mechanics.PrivateGoalFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player player;

    @BeforeEach
    void setUp() {
        PrivateGoalFactory privateGoalFactory = new PrivateGoalFactory();
        player = new Player("Michelangelo", privateGoalFactory.makePrivateGoal());
    }

    /*
    @Test
    void addCardToBookshelf() {
        player.getBookshelf().addCard(0, new Card("white1.png", CardType.FRAMES));
        assertEquals(CardType.FRAMES, player.getBookshelf().getColumn(0)[5].getCard().getType());
    }

     */

    @Test
    void addScore() {
        assertEquals(0, player.getScore());
        player.addScore(68);
        assertEquals(68, player.getScore());
    }

    /*
    @Test
    void isBookshelfFull() {
        for(int i = 0; i<5; i++){
            for(int j = 0; j < 6; j++) player.getBookshelf().addCard(i, new Card("white1.png", CardType.FRAMES));
        }
        assertTrue(player.getBookshelf().isBookshelfFull());
    }

     */

    @Test
    void scoreCommonGoal(){
        assertFalse(player.isCommonGoalScored(0));
        player.scoreCommonGoal(0);
        assertTrue(player.isCommonGoalScored(0));
    }

    @Test
    void usernameGetter(){
        assertEquals("Michelangelo", player.getUsername());
    }

}