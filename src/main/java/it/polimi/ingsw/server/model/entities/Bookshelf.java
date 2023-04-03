/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities;

import it.polimi.ingsw.exceptions.AddCardException;

public class Bookshelf {

    //REGION ATTRIBUTES
    private final Cell[][] bookshelf;
    //END REGION

    //REGION CONSTRUCTOR
    public Bookshelf(){
        //TODO: initialization of every Cell otherwise it has to be done in the respective test
        bookshelf = new Cell[6][5];
        for(int i=0; i<6; i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Cell();
    }
    //END REGION

    //REGION METHODS
    public void addCard(int column, Card card) throws AddCardException { //TODO add code try/catch where it will be used
        int i = 0;
        while(!bookshelf[i][column].isCellEmpty()) i++;
        bookshelf[i][column].setCard(card);
    }

    public Cell getCell(int row, int column){
        return bookshelf[row][column];
    }



    //END REGION
}
