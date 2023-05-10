package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.Cell;

/*
 * Four tiles of the same type in the four corners of the bookshelf.
 */

public class CommonGoal8 extends CommonGoal implements Goal {

    public CommonGoal8() {
        super("Four tiles of the same type in the four corners of the bookshelf.");
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        Cell c1, c2, c3, c4;

        try {
            c1 = bookshelf.getCell(0,0);
            c2 = bookshelf.getCell(0,4);
            c3 = bookshelf.getCell(5,0);
            c4 = bookshelf.getCell(5,4);

            if(c1.isCellEmpty() || c2.isCellEmpty() || c3.isCellEmpty() || c4.isCellEmpty()) return 0;
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(c1.getCard().sameType(c2.getCard()) && c3.getCard().sameType(c4.getCard()) && c2.getCard().sameType(c4.getCard())) return getScore();
        else return 0;
    }
}
