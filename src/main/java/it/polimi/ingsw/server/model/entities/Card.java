/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities;

public class Card {

    //region ATTRIBUTES
    private final String img;
    private final int color; //also we can use Enumeration<...>
    //endregion

    //region CONSTRUCTOR
    public Card(String imgName, int color){
        this.color = color;
        this.img = imgName;
    }
    //endregion

    //region METHODS
    public String getImg() {
        return img;
    }
    public int getColor() {
        return color;
    }
    public boolean sameColor(Card c){
        return (color == c.color);
    }
    public boolean sameCard(Card card) {
        return card.getColor() == this.color && card.getImg().equals(this.img);
    }
    //endregion

}
