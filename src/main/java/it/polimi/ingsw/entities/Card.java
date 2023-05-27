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
    /**
     * @return The path of the resource starting from the resources root
     */
    public String getImgPath() {
        return "/assets/Cards/" + imageName;
    }

    public CardType getType() {
        return type;
    }

    /**
     * Checks if two cards have the same color
     * @param card card to compare
     * @return true if the two cards have the same color
     */
    public boolean sameType(Card card){
        return (this.type == card.getType());
    }
    //endregion

}
