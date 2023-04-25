package it.polimi.ingsw.server.model.entities;

public class SpecialCell extends Cell{
    private int row;
    private int column;

    public SpecialCell(int row, int column, TileType type) {
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
