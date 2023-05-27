package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal12Test {

    Bookshelf bookshelf;
    CommonGoal12 cg12;
    @BeforeEach
    void setUp() {
        cg12 = new CommonGoal12();
        bookshelf= new Bookshelf();
    }

    @Test
    void checkGoalCaseTrue() {
        int cnt = 6;
        for(int i=0; i<5; i++) {
            for(int j = 0; j < cnt; j++) bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            cnt--;
        }

        assertEquals(8, cg12.checkGoal(bookshelf));
    }

    @Test
    void checkGoalCaseFalse() {
        int cnt = 4;
        for(int i=0; i<5; i++) {
            for(int j = 0; j < cnt; j++) bookshelf.addCard(i, new Card("img.png", CardType.FRAMES));
            cnt--;
        }

        assertEquals(0, cg12.checkGoal(bookshelf));
    }
}