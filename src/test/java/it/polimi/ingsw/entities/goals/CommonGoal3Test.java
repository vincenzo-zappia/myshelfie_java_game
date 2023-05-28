package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal3Test {
    private static Bookshelf bookshelf;
    private static CommonGoal3 cg3;


    @BeforeEach
    void setUp() {
        bookshelf = new Bookshelf();
        cg3 = new CommonGoal3();
    }

    @Test
    void checkGoalVerticalHorizontal() {

        for(int i=0; i<4; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.GAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.PLANTS));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }

        int score = cg3.checkGoal(bookshelf);
        assertEquals(8, score);
    }

    @Test
    void checkGoalLShape() {

        bookshelf.addCard(0, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(0, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(0, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(1, new Card("img.png", CardType.FRAMES));

        bookshelf.addCard(1, new Card("img.png", CardType.CATS));
        bookshelf.addCard(1, new Card("img.png", CardType.CATS));
        bookshelf.addCard(1, new Card("img.png", CardType.CATS));
        bookshelf.addCard(0, new Card("img.png", CardType.CATS));

        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(3, new Card("img.png", CardType.PLANTS));

        bookshelf.addCard(3, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(3, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(3, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(2, new Card("img.png", CardType.TROPHIES));

        int score = cg3.checkGoal(bookshelf);
        assertEquals(8, score);
    }

    @Test
    void checkGoalCaseFalse(){
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(3, new Card("img.png", CardType.PLANTS));

        bookshelf.addCard(3, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(3, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(3, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(2, new Card("img.png", CardType.TROPHIES));

        int score = cg3.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    @Test
    void checkGoalCritical(){
        bookshelf.addCard(0, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(0, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(0, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(1, new Card("img.png", CardType.FRAMES));

        bookshelf.addCard(0, new Card("img.png", CardType.CATS));
        bookshelf.addCard(1, new Card("img.png", CardType.CATS));
        bookshelf.addCard(1, new Card("img.png", CardType.CATS));
        bookshelf.addCard(1, new Card("img.png", CardType.CATS));

        bookshelf.addCard(2, new Card("img.png", CardType.GAMES));
        bookshelf.addCard(2, new Card("img.png", CardType.GAMES));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));
        bookshelf.addCard(2, new Card("img.png", CardType.PLANTS));

        bookshelf.addCard(3, new Card("img.png", CardType.BOOKS));
        bookshelf.addCard(3, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(3, new Card("img.png", CardType.BOOKS));
        bookshelf.addCard(3, new Card("img.png", CardType.FRAMES));
        bookshelf.addCard(3, new Card("img.png", CardType.CATS));
        bookshelf.addCard(3, new Card("img.png", CardType.GAMES));

        bookshelf.addCard(4, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(4, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(4, new Card("img.png", CardType.TROPHIES));
        bookshelf.addCard(4, new Card("img.png", CardType.TROPHIES));

        int score = cg3.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}