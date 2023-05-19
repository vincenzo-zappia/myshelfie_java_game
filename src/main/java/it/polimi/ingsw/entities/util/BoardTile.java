/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: none
 */

package it.polimi.ingsw.entities.util;

/**
 * Tile that adds a boolean attribute to check whether it is a playable one (needed to give the board, a tile matrix, its shape)
 */
public class BoardTile extends Tile {
    private Boolean active;

    public BoardTile(){
        super();
        active = false;
    }

    public void setTileActive(){
        active = true;
    }
    public boolean isTileActive(){
        return active;
    }

}
