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
    private final TileType type; //also we can use Enumeration<...>
    //endregion

    //region CONSTRUCTOR
    public Card(String imgName, TileType type){
        this.type = type;
        this.img = imgName;
    }
    //endregion

    //region METHODS
    public String getImg() {
        return img;
    }
    public TileType getType() {
        return type;
    }
    public boolean sameType(Card c){
        return (this.type == c.getType());
    }
    public boolean sameCard(Card card) {
        return card.getType() == this.type && card.getImg().equals(this.img);
    }
    //endregion

}
