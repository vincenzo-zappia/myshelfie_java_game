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

/**
 * Generic tile that can contain a game card
 */
public class Tile implements Serializable {
    private Card card;
    private boolean empty;

    public Tile(){
        empty=true;
    }

    public void setCard(Card card) {
        this.card = card;
        empty = false;
    }
    public Card getCard() throws CellGetCardException {
        if(!isTileEmpty()) return card;
        else return null;
    }
    public void setTileEmpty(){
        empty=true;
    }
    public boolean isTileEmpty(){
        return empty /*|| card == null*/;
    }

}
