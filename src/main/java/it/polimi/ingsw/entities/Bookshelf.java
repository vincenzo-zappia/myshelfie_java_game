/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: the bookshelf is an attribute of player (info for test purposes)
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.exceptions.FullColumnException;
import it.polimi.ingsw.exceptions.GetCardException;
import it.polimi.ingsw.entities.util.Tile;

import java.io.Serializable;

/**
 * Bookshelf of a player where the cards picked from the board can be added
 */
public class Bookshelf implements Serializable {
    private final Tile[][] bookshelf;

    //region CONSTRUCTOR
    public Bookshelf(){
        bookshelf = new Tile[6][5];

        //Initializing every single tile of the bookshelf
        for(int i = 0; i < 6; i++) for(int j = 0; j < 5; j++) bookshelf[i][j] = new Tile();
    }
    //endregion

    //region METHODS
    /**
     * Adds a card to the bookshelf
     * @param column where to insert the card
     * @param card to add
     * @throws FullColumnException when the column is full
     */
    public void addCard(int column, Card card) throws FullColumnException {
        int i = 5;
        if(!bookshelf[0][column].isTileEmpty()) throw new FullColumnException();
        while(!bookshelf[i][column].isTileEmpty() && i>0) i--;
        bookshelf[i][column].setCard(card);
    }

    /**
     * Checks whether the bookshelf is full
     * @return if the bookshelf is full
     */
    public boolean isBookshelfFull(){

        //Returning false if a single tile is empty
        boolean sentinel;
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 5; j++){
                sentinel = bookshelf[i][j].isTileEmpty();
                if(sentinel) return false;
            }
        }
        return true;

    }

    /**
     * Counts the number of cards in a column
     * @param column in which to count the cards
     * @return number of cards in the column
     */
    public int cardsInColumn(int column) {
        int i = 5, count = 0;
        while (i >= 0 && !bookshelf[i][column].isTileEmpty()){
            i--;
            count++;
        }
        return count;
    }

    /**
     * Counts the number of cards in a row
     * @param row in which to count the cards
     * @return number of cards in the row
     */
    public int cardsInRow(int row){
        int i = 0;
        while (i < 5 && !bookshelf[row][i].isTileEmpty()) i++;
        return i;
    }

    /**
     * Extracts the colors of the cards in the bookshelf
     * @return 6x5 matrix of color-codifying integers
     */
    public int[][] getBookshelfColors() {
        int[][] matrix = new int[6][5];
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++) {
                try {
                    //Saving the codified value in the matrix
                    if(!getBookshelfTile(i,j).isTileEmpty()) matrix[i][j] = getBookshelfTile(i,j).getCard().getType().ordinal();

                    //Using the value UNAVAILABLE(104) if a tile has no card in it
                    else matrix[i][j] = Goal.UNAVAILABLE;

                } catch (GetCardException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return matrix;
    }
    //endregion

    //region GETTER
    public Tile[][] getBookshelf(){
        return bookshelf;
    }
    public Tile getBookshelfTile(int row, int column){
        return bookshelf[row][column];
    }
    public Tile[] getColumn(int column){
        Tile[] result = new Tile[6];
        for(int i = 0; i < bookshelf.length; i++) result[i] = bookshelf[i][column];
        return result;
    }
    public Tile[] getRow(int row) {
        Tile[] res = new Tile[5];
        for (int i = 0; i<5; i++){
            if(bookshelf[row][i] != null)res[i] = bookshelf[row][i];
        }
        return res;
    }
    //endregion

}
