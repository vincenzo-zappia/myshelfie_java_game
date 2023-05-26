/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.util.CardType;

import java.io.Serializable;

/**
 * Card of the game identified by an image and a color
 */
public class Card implements Serializable {
    private final String imageName;
    private final CardType type;

    public Card(String imageName, CardType type){
        this.imageName = imageName;
        this.type = type;
    }

    //region METHODS
    public String getImgPath() {
        return "/assets/Cards/" + imageName; //TODO: Sistemare path name
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
