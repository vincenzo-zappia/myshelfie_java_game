/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: none
 */

package it.polimi.ingsw.util;

import java.io.Serializable;

public class BoardCell extends Cell {

    //region ATTRIBUTES
    private Boolean active; //see board doc/code
    //endregion

    //region CONSTRUCTOR
    public BoardCell(){
        super();
        active = false;
    }
    //endregion

    //region METHODS
    public void setCellActive(){
        active = true;
    }
    public boolean isCellActive(){
        return active;
    }
    //endregion

}
