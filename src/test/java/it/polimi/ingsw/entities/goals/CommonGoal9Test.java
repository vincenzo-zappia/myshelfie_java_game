package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal9Test {
    private static Bookshelf bookshelf;
    private static CommonGoal9 cg9;

    @BeforeAll
    static void init(){cg9 = new CommonGoal9();}

    @BeforeEach
    void setUp() {bookshelf=new Bookshelf();}

    @Test
    void checkGoal1() {
        try {
            for(int i=0; i<2; i++) {
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.CATS));
                bookshelf.addCard(i, new Card("img.png", CardType.BOOKS));
                bookshelf.addCard(i, new Card("img.png", CardType.GAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.TROPHIES));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            }
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg9.checkGoal(bookshelf);
        assertNotEquals(8, score);
    }

    @Test
    void checkGoal2() {
        try {
            for(int i=0; i<5; i++) {
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.CATS));
                bookshelf.addCard(i, new Card("img.png", CardType.BOOKS));
                bookshelf.addCard(i, new Card("img.png", CardType.GAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            }
        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg9.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}