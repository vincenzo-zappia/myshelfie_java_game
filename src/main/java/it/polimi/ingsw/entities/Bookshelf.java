/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.exceptions.AddCardException;

public class Bookshelf {

    //REGION ATTRIBUTES
    private Cell[][] bookshelf;
    //END REGION

    //REGION CONSTRUCTOR
    public Bookshelf(){ bookshelf = new BoardCell[6][5]; }
    //END REGION

    //REGION METHODS
    public void addCard(int column, Card card) throws AddCardException { //TODO add code try/catch where it will be used
        int i = 0;
        while(!bookshelf[i][column].isCellEmpty()) i++;
        bookshelf[i][column].setCard(card);
    }
    //END REGION
}
