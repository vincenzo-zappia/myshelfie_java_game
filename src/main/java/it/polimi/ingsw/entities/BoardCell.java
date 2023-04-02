/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: none
 */

package proj.polimi.testrj45.entities;

public class BoardCell extends Cell {

    //region Attributes
    private Boolean active; //see board doc/code
    //endregion

    //region Costruttore
    public BoardCell(){
        super();
        active = false;
    }
    //endregion

    //region Methods
    public void setCellActive(){
        active = true;
    }
    public boolean isCellActive(){
        return active;
    }

    //endregion
}
