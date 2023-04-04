/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities;

public class BoardCell extends Cell {

    //REGION ATTRIBUTES
    private Boolean active; //see board doc/code
    //END REGION

    //REGION CONSTRUCTOR
    public BoardCell(){
        super();
        active = false;
    }
    //END REGION

    //REGION METHODS
    public void setCellActive(){
        active = true;
    }
    public boolean isCellActive(){
        return active;
    }
    //END REGION

}
