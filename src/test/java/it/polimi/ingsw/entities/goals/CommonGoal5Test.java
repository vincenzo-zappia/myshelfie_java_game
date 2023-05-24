package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.FullColumnException;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal5Test {
    private static Bookshelf bookshelf;
    private static CommonGoal5 cg5;

    @BeforeAll
    static void init(){cg5 = new CommonGoal5();}

    @BeforeEach
    void setUp() {bookshelf=new Bookshelf();}

    @Test
    void checkGoal1() {
        for(int i=0; i<1; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.CATS));
            bookshelf.addCard(i, new Card("img.png", CardType.BOOKS));
            bookshelf.addCard(i, new Card("img.png", CardType.GAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.PLANTS));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg5.checkGoal(bookshelf);
        assertNotEquals(8, score);
    }

    @Test
    void checkGoal2() {
        for(int i=0; i<3; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.CATS));
            bookshelf.addCard(i, new Card("img.png", CardType.BOOKS));
            bookshelf.addCard(i, new Card("img.png", CardType.GAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg5.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}