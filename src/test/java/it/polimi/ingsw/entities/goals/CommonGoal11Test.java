package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal11Test {

    private static Bookshelf bookshelf;
    private static Tile[][] matrix;
    private static Goal cg11;

    @BeforeEach
    void startup() {
        bookshelf = new Bookshelf();
        cg11= new CommonGoal11();
        matrix = bookshelf.getBookshelf();
    }

    @Test
    void diagonalWithAnInfectedType() {
        for(int i = 0; i < 4; i++) matrix[i][i].setCard(new Card("img.png", CardType.FRAMES));
        matrix[4][4].setCard(new Card("img.png", CardType.BOOKS));

        //Verify that the goal has not been scored
        assertEquals(0, cg11.checkGoal(bookshelf));
    }

    @Test
    void diagonalWithoutACard() {
        for(int i = 0; i < 4; i++) matrix[5-i][i].setCard(new Card("img.png", CardType.FRAMES));

        //Verify that the goal has not been scored
        assertEquals(0, cg11.checkGoal(bookshelf));
    }

    @Test
    void diagonalOfSameType() {
        for(int i = 0; i < 5; i++) matrix[5-i][4-i].setCard(new Card("img.png", CardType.FRAMES));

        //Verify that the goal is scored
        assertEquals(8, cg11.checkGoal(bookshelf));
    }

}
