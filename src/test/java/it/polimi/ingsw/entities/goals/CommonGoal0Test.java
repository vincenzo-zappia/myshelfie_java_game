package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.goals.CommonGoal0;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonGoal0Test {
    private static CommonGoal0 cg0;
    public static Bookshelf b;

    @BeforeEach
    void Init(){
        cg0=new CommonGoal0();
        b=new Bookshelf();
    }

    @Test
    void twoGroups2x2() {
        for(int i=0; i<2; i++) {
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
        }

        for(int i=3; i<5; i++) {
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
        }
        int score = cg0.checkGoal(b);
        assertEquals(4, score);
    }

    @Test
    void testGroup2x3() {
        for(int i=0; i<3; i++) {
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
        }

        for(int i=3; i<5; i++) {
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
        }
        int score = cg0.checkGoal(b);
        assertEquals(6, score);
    }

    @Test
    void testGroup4x4() {
        for(int i=0; i<4; i++) {
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
        }
        int score = cg0.checkGoal(b);
        assertEquals(16, score);
    }

    @Test
    void fakeGroup4x4() {
        for(int i=0; i<4; i++) {
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
            b.addCard(i, new Card("img.png", CardType.FRAMES));
        }
        int score = cg0.checkGoal(b);
        assertEquals(12, score);
    }

    @Test
    void onlyRowsGroups() {
        for(int i=0; i<5; i++) {
            b.addCard(i, new Card("img.png", CardType.FRAMES));
            b.addCard(i, new Card("img.png", CardType.CATS));
            b.addCard(i, new Card("img.png", CardType.PLANTS));
            b.addCard(i, new Card("img.png", CardType.GAMES));
            b.addCard(i, new Card("img.png", CardType.TROPHIES));
            b.addCard(i, new Card("img.png", CardType.BOOKS));
        }
        int score = cg0.checkGoal(b);
        assertEquals(5, score);
    }

    @Test
    void noGroups() {

        b.addCard(3, new Card("img.png", CardType.BOOKS));
        int score = cg0.checkGoal(b);
        assertEquals(0, score);
    }
}
