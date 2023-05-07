/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: the bookshelf is an attribute of player (info for test purposes)
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.Cell;

public class Bookshelf {

    //region ATTRIBUTES
    private final Cell[][] bookshelf;
    //endregion

    //region CONSTRUCTOR
    public Bookshelf(){
        bookshelf = new Cell[6][5];
        for(int i=0; i<6; i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Cell();
    }
    //endregion

    //region METHODS

    /**
     * check if the bookshelf is full
     * @return false while bookshelf is not full
     */
    public boolean checkIfFull(){
        boolean sentinel = false;
        for(int i=0; i<6; i++) {
            for(int j=0; j<5; j++){
                sentinel = bookshelf[i][j].isCellEmpty();   // if a single cell of bookshelf is empty,
                if(sentinel) return false;                  // the method immediately return false
            }
        }
        return true;
    }


    /**
     * method that add card in the bookshelf (by columns)
     * @param column where player want to insert card
     * @param card Card to insert
     * @throws AddCardException when the column is full
     */
    public void addCard(int column, Card card) throws AddCardException {
        int i = 5;
        if(!bookshelf[0][column].isCellEmpty()) throw new AddCardException("Colonna piena!");
        while(!bookshelf[i][column].isCellEmpty() && i>0) i--;
        bookshelf[i][column].setCard(card);
    }

    /**
     * count the number of cards in a single column
     * @param column of the bookshelf
     * @return number of cards
     */
    public int cardsInColumn(int column) {
        int i=5, count=0;
        while (i>=0 && !bookshelf[i][column].isCellEmpty()){
            i--;
            count++;
        }
        return count;
    }

    /**
     * method that returns a matrix 6x5 of only colours of the cards
     * @return the matrix itself
     */
    public int[][] getMatrixColors() {
        int[][] x = new int[6][5];

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++) {
                System.out.println("hhvkkld");
                try {
                    if(!getCell(i,j).isCellEmpty()) x[i][j] = getCell(i,j).getCard().getType().ordinal(); //save the value in matrix x[][]
                    else x[i][j] = Goal.UNAVAILABLE; //if a cell is empty, use the value UNAVAILABLE(104) to detect in the int matrix

                } catch (CellGetCardException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return x;
    }
    //endregion

    //region GETTER AND SETTER
    public Cell getCell(int row, int column){
        return bookshelf[row][column];
    }
    public Cell[] getRow(int row) {
        return bookshelf[row];
    }
    //endregion

}
