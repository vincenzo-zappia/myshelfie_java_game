package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.FullColumnException;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal7Test {
    private static Bookshelf bookshelf;
    private static CommonGoal7 cg7;

    @BeforeAll
    static void init(){cg7 = new CommonGoal7();}

    @BeforeEach
    void setUp() {bookshelf=new Bookshelf();}

    @Test
    void checkGoal1() {
            bookshelf.addCard(3, new Card("img.png", CardType.GAMES));
        int score = cg7.checkGoal(bookshelf);
        assertNotEquals(8, score);
    }

    @Test
    void checkGoal2() {
        try {
            for(int i=0; i<5; i++) {
                bookshelf.addCard(i, new Card("img.png", CardType.BOOKS));
                bookshelf.addCard(i, new Card("img.png", CardType.GAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            }
        } catch (FullColumnException e) {
            throw new RuntimeException(e);
        }

        int score = cg7.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}