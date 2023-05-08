/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.util.CardType;

import java.io.Serializable;

public class Card implements Serializable {

    //region ATTRIBUTES
    private final String img;
    private final CardType type;
    //endregion

    //region CONSTRUCTOR
    public Card(String imgName, CardType type){
        this.type = type;
        this.img = imgName;
    }
    //endregion

    //region METHODS
    public String getImgPath() {
        return "/assets/Cards/" + img;
    }
    public CardType getType() {
        return type;
    }
    public boolean sameType(Card c){
        return (this.type == c.getType());
    }
    public boolean sameCard(Card card) {
        return card.getType() == this.type && card.getImgPath().equals(getImgPath());
    }
    //endregion

}
