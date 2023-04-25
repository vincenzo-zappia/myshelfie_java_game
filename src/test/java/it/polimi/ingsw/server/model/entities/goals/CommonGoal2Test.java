package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.server.model.entities.Card;
import it.polimi.ingsw.server.model.entities.TileType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal2Test {
    private static Bookshelf bookshelf;
    private static CommonGoal2 cg2;

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
            for(int i=0; i<6; i++) bookshelf.addCard(0, new Card("img.png", TileType.values()[i]));
            for(int i=0; i<6; i++) bookshelf.addCard(4, new Card("img.png", TileType.values()[i]));

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg2.checkGoal(bookshelf);
        assertEquals(8, score);
    }
}
