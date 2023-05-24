package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.exceptions.FullColumnException;
import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CommonGoal1Test {
    private static Bookshelf bookshelf;
    private static CommonGoal1 cg1;

    @BeforeEach
    public void refreshBookshelf(){
        cg1 = new CommonGoal1();
        bookshelf = new Bookshelf();
    }

    /**
     * Recognition of two separate 2x2 groups with same color
     */
    @Test
    public void normalGroups2x2() {
        for(int i=0; i<2; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }

        for(int i=3; i<5; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg1.checkGoal(bookshelf);
        assertEquals(8, score);
    }

    /**
     * Recognition of two separate 2x2 different groups
     */
    @Test
    public void infectedGroups2x2() {
        for(int i=0; i<2; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }

        for(int i=3; i<5; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.CATS));
        }
        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    /**
     * test if method recognize only separate groups
     */
    @Test
    public void matrix2x3() {
        for(int i=0; i<3; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    /**
     * test if method recognize only separate groups
     */
    @Test
    public void matrix2x4() {
            for(int i=0; i<4; i++) {
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            }
        int score = cg1.checkGoal(bookshelf);
        assertEquals(8, score);
    }

    /**
     * test if method recognize only separate groups
     */
    @Test
    public void matrix3x2() {
        for(int i=0; i<2; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    /**
     * test if method recognize only separate groups
     */
    @Test
    public void matrix4x2() {
        for(int i=0; i<2; i++) {
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg1.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}