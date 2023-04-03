package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.server.model.entities.Card;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoal1Test {
    private static Bookshelf bookshelf;
    private static CommonGoal1 cg1;

    @BeforeAll
    public static void inizialize(){
        cg1 = new CommonGoal1();
    }

    @BeforeEach
    public void refreshBookshelf(){
        bookshelf = new Bookshelf();
    }

    /*
     * Il primo test verifica un caso in cui ci sono 2 gruppi separati 2x2 dello stesso colore
     * Il secondo test verifica un caso in cui ci sono 2 gruppi separati 2x2 con una Card di colore diverso
     * I test 3 e 4 (matrix2x3 & matrix2x4) servono per verificare che i gruppi di tessere 4x4 siano sparati (row)
     * I test 5 e 6 (matrix2x3 & matrix2x4) servono per verificare che i gruppi di tessere 4x4 siano sparati (column)
     */

    @Test
    public void normalGroups2x2(){
        try {

            for(int i=0; i<2; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

            for(int i=3; i<5; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg1.checkGoal(bookshelf);
        assertEquals(1, score);
    }

    @Test
    public void infectedGroups2x2(){
        try {

            for(int i=0; i<2; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

            for(int i=3; i<5; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 1));
            }

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    @Test
    public void matrix2x3(){

        try {

            for(int i=0; i<3; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
        //TODO: creating a bookshelf with the pattern recognized by CommonGoal1 and asserting the validity of its recognition
    }

    @Test
    public void matrix2x4(){
        try {
            for(int i=0; i<4; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    @Test
    public void matrix3x2(){
        try {

            for(int i=0; i<2; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }

    @Test
    public void matrix4x2(){
        try {

            for(int i=0; i<2; i++) {
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
                bookshelf.addCard(i, new Card("img.png", 0));
            }

        } catch (AddCardException e) {
            throw new RuntimeException(e);
        }

        int score = cg1.checkGoal(bookshelf);
        assertEquals(0, score);
    }


}