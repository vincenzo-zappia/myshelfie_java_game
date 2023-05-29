package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal10Test {

    private static Bookshelf bookshelf;
    private static Tile[][] matrix;
    private static Goal cg10;

    @BeforeEach
    void startup() {
        bookshelf = new Bookshelf();
        cg10 = new CommonGoal10();
        matrix = bookshelf.getBookshelf();
    }

    @Test
    void xWithoutAElement() {

        brances(matrix);

        //Verify that the goal has not been scored
        assertEquals(0, cg10.checkGoal(bookshelf));
    }

    @Test
    void xWithAnInfectedType() {

        brances(matrix);

        matrix[5][1].setCard(new Card("img.png", CardType.FRAMES));
        matrix[4][1].setCard(new Card("img.png", CardType.BOOKS));

        //Verify that the goal has not been scored
        assertEquals(0, cg10.checkGoal(bookshelf));
    }

    @Test
    void xOfSameType() {
        brances(matrix);

        matrix[5][1].setCard(new Card("img.png", CardType.FRAMES));
        matrix[4][1].setCard(new Card("img.png", CardType.FRAMES));

        //Verify that the goal is scored
        assertEquals(8, cg10.checkGoal(bookshelf));
    }

    private static void brances(Tile[][] matrix){
        matrix[5][0].setCard(new Card("img.png", CardType.FRAMES));
        matrix[4][0].setCard(new Card("img.png", CardType.BOOKS));
        matrix[3][0].setCard(new Card("img.png", CardType.FRAMES));

        matrix[5][2].setCard(new Card("img.png", CardType.FRAMES));
        matrix[4][2].setCard(new Card("img.png", CardType.BOOKS));
        matrix[3][2].setCard(new Card("img.png", CardType.FRAMES));
    }

}
