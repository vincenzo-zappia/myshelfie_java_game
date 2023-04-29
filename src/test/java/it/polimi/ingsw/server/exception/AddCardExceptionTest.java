package it.polimi.ingsw.server.exception;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.util.CardType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AddCardExceptionTest {
    private static Bookshelf b;

    @BeforeAll
    public static void inizialize(){
        b = new Bookshelf();
    }

    @Test
    public void test1() throws AddCardException {
        for(int i=0; i<6; i++){
            b.addCard(0, new Card("books1.png", CardType.BOOKS));
        }
        assertThrows(AddCardException.class, () -> b.addCard(0, new Card("books1.png", CardType.BOOKS)));
    }
}
