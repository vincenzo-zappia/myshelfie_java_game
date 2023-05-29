package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal8Test {

    private static Bookshelf bookshelf;
    private static Tile[][] matrix;
    private static Goal cg8;

    @BeforeEach
    void startup() {
        cg8 = new CommonGoal8();
        bookshelf = new Bookshelf();
        matrix = bookshelf.getBookshelf();
    }

    @Test
    void twoCornersOfSameType() {

        matrix[5][0].setCard(new Card("img.png", CardType.FRAMES));
        matrix[5][4].setCard(new Card("img.png", CardType.FRAMES));

        //Verify that the goal has not been scored
        assertEquals(0, cg8.checkGoal(bookshelf));
    }

    @Test
    void twoCornersOfSameTypeForOne() {
        for(int i = 0; i < 5 ; i++){
            matrix[i][0].setCard(new Card("img.png", CardType.FRAMES));
            matrix[i][4].setCard(new Card("img.png", CardType.FRAMES));
        }
        matrix[0][0].setCard(new Card("img.png", CardType.BOOKS));
        matrix[0][4].setCard(new Card("img.png", CardType.BOOKS));

        //Verify that the goal has not been scored
        assertEquals(0, cg8.checkGoal(bookshelf));
    }


    @Test
    void allCornerOfSameType() {
        for(int i = 0; i < 6 ; i++){
            matrix[i][0].setCard(new Card("img.png", CardType.FRAMES));
            matrix[i][4].setCard(new Card("img.png", CardType.FRAMES));
        }

        //Verify that the goal is scored
        assertEquals(8, cg8.checkGoal(bookshelf));
    }


}
