package it.polimi.ingsw.util;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.util.CardType;
import it.polimi.ingsw.util.Cell;

public class SpecialCell extends Cell {
    private int row;
    private int column;

    public SpecialCell(int row, int column, CardType type) {
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
