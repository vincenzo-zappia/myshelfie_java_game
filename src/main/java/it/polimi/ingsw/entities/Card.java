/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package proj.polimi.testrj45.entities;

import java.util.Random;

public class Card {

    //REGION ATTRIBUTES
    private String img;
    private int color; //also we can use Enumeration<...>
    //END REGION

    //REGION CONSTRUCTOR
    public Card(String imgName, int color){
        this.color = color;
        this.img = imgName;
    }
    //END REGION

    //REGION METHODS
    public String getImg() {
        return img;
    }

    public int getColor() {
        return color;
    }
    //ENDREGION
}
