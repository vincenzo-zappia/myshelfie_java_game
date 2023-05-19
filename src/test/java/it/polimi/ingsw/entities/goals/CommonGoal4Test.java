package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.FullColumnException;
import it.polimi.ingsw.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal4Test {

    private static Bookshelf bookshelf;
    private static CommonGoal4 cg4;

    @BeforeAll
    static void init(){cg4 = new CommonGoal4();}

    @BeforeEach
    void setUp() {bookshelf=new Bookshelf();}

    @Test
    void checkGoal() {
        try {
            for(int i=0; i<3; i++) {
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
                bookshelf.addCard(i, new Card("img.png", CardType.CATS));
                bookshelf.addCard(i, new Card("img.png", CardType.CATS));
                bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            }
        } catch (FullColumnException e) {
            throw new RuntimeException(e);
        }

        int score = cg4.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}