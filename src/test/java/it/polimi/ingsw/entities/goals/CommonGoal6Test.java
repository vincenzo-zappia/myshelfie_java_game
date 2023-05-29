package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommonGoal6Test {

    private static Bookshelf bookshelf;
    private static Tile[][] matrix;
    private static Goal cg6;


    @BeforeEach
    void startup(){
        bookshelf = new Bookshelf();
        cg6 = new CommonGoal6();
        matrix = bookshelf.getBookshelf();
    }

    @Test
    void singleDifferentRow() {
        //Filling the last row with different cards
        for(int i=0; i < 5; i++) matrix[5][i].setCard(new Card("img.png", CardType.values()[i]));

        //Verify that the goal has not been scored
        assertEquals(0, cg6.checkGoal(bookshelf));
    }

    @Test
    void twoRowsWithAnInfectedType(){
        //Insertion in the last row 4 cards of different types
        for(int i=0; i < 4; i++) matrix[5][i].setCard(new Card("img.png", CardType.values()[i]));

        //Insertion in the 5th row 4 cards of different type
        for(int i=0; i < 4; i++) matrix[4][i].setCard(new Card("img.png", CardType.values()[i]));

        //Adding 2 cards of the same type to the last column
        matrix[5][4].setCard(new Card("img.png", CardType.values()[0]));
        matrix[4][4].setCard(new Card("img.png", CardType.values()[0]));

        //Verify that the goal has not been scored
        assertEquals(0, cg6.checkGoal(bookshelf));

    }

    @Test
    void twoIncompleteRowsOfDifferentType() {
        //Filling the last row with different cards
        for(int i=0; i < 4; i++) matrix[5][i].setCard(new Card("img.png", CardType.values()[i]));

        //Filling the 5th row with different cards
        for(int i=0; i < 4; i++) matrix[4][i].setCard(new Card("img.png", CardType.values()[i]));

        //Verify that the goal is scored
        assertEquals(0, cg6.checkGoal(bookshelf));
    }

    @Test
    void twoRowsOfDifferentType() {
        //Filling the last row with different cards
        for(int i=0; i < 5; i++) matrix[5][i].setCard(new Card("img.png", CardType.values()[i]));

        //Filling the 5th row with different cards
        for(int i=0; i < 5; i++) matrix[4][i].setCard(new Card("img.png", CardType.values()[i]));

        //Verify that the goal is scored
        assertEquals(8, cg6.checkGoal(bookshelf));
    }

    @Test
    void twoSeparateRowsOfDifferentType() {
        //Filling the last row with different cards
        for(int i=0; i < 5; i++) matrix[5][i].setCard(new Card("img.png", CardType.values()[i]));

        //Filling the 5th row
        for(int i=0; i < 5; i++) matrix[4][i].setCard(new Card("img.png", CardType.values()[0]));

        //Filling the 4th row with different cards
        for(int i=0; i < 5; i++) matrix[3][i].setCard(new Card("img.png", CardType.values()[i]));

        //Verify that the goal is scored
        assertEquals(8, cg6.checkGoal(bookshelf));
    }

}
