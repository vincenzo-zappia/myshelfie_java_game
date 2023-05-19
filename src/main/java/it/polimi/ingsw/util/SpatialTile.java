package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;

/**
 * Tile that adds its coordinates as attributes to allow for pattern recognition
 */
public class SpatialTile extends Tile {
    private final int row;
    private final int column;

    public SpatialTile(int row, int column, CardType type) {
        super();
        setCard(new Card("img.png", type));
        this.row = row;
        this.column = column;
    }
    
    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    
}
