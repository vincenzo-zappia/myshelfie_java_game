/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 26/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;
import java.io.Serializable;

public class Cell implements Serializable {

    //region ATTRIBUTES
    private Card card;
    private boolean empty;
    //endregion

    //region CONSTRUCT
    public Cell(){
        empty=true;
    }
    //endregion

    //region METHODS
    public Card getCard() throws CellGetCardException {
        if(!isCellEmpty()) return card;
        else return null;
    }
    public void setCard(Card card) {
        this.card = card;
        empty = false;
    }
    public void setCellEmpty(){
        empty=true;
    }
    public boolean isCellEmpty(){
        return empty /*|| card == null*/;
    }
    //endregion

}
