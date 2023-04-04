/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities;

import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.goals.Goal;

public class Bookshelf {

    //REGION ATTRIBUTES
    private final Cell[][] bookshelf;
    //TODO: how to manage attribution of a bookshelf to a player? (Not in constructor, otherwise tests get fu***d
    //private final Player player;
    //END REGION

    //REGION CONSTRUCTOR
    public Bookshelf(){
        bookshelf = new Cell[6][5];
        for(int i=0; i<6; i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Cell();
    }
    //END REGION

    //REGION METHODS
    public void addCard(int column, Card card) throws AddCardException { //TODO add code try/catch where it will be used
        int i = 5;
        while(!bookshelf[i][column].isCellEmpty() && i>=0) i--;
        bookshelf[i][column].setCard(card);
    }

    //TODO: rename to "cardsInColumn()" and using the method to implement "isBookshelfFull()"
    public int numOfCards(int column){
        int i=5, count=0;
        while (!bookshelf[i][column].isCellEmpty() && i>=0){
            i--;
            count++;
        }
        return count;
    }

    //TODO: create the method "isBookshelfFull()"

    //TODO: rename to "getMatrixColors"
    public int[][] getColorMatrix(){
        int[][] x = new int[6][5];

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++) {
                try {
                    if(!getCell(i,j).isCellEmpty())x[i][j] = getCell(i,j).getCard().getColor(); // save the value in matrix x[][]
                    else x[i][j] = Goal.UNAVAILABLE; //if a cell is empty, use the value UNAVAILABLE(104) to detect in the int matrix

                } catch (CellGetCardException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return x;
    }
    //END REGION

    //REGION GETTER AND SETTER
    public Cell getCell(int row, int column){
        return bookshelf[row][column];
    }
    public Cell[] getRow(int row) {
        return bookshelf[row];
    }
    //END REGION

}
