package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.entities.goals.CommonGoal2;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal2Test {
    private static Bookshelf bookshelf;
    private static Goal cg2;

    @BeforeAll
    public static void inizialize(){
        cg2 = new CommonGoal2();
    }

    @BeforeEach
    public void refreshBookshelf(){
        bookshelf = new Bookshelf();

    }

    @Test
    public void test1(){
        try {
            for(int i=0; i<6; i++) bookshelf.addCard(0, new Card("img.png", CardType.values()[i]));
            for(int i=0; i<6; i++) bookshelf.addCard(4, new Card("img.png", CardType.values()[i]));

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg2.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}
