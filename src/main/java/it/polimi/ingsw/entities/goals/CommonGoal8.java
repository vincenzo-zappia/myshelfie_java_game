package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.Tile;

/*
 * Four tiles of the same type in the four corners of the bookshelf.
 */

public class CommonGoal8 extends CommonGoal implements Goal {

    public CommonGoal8() {
        super("Four tiles of the same type in the four corners of the bookshelf.");
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        Tile c1, c2, c3, c4;

        try {
            c1 = bookshelf.getBookshelfTile(0,0);
            c2 = bookshelf.getBookshelfTile(0,4);
            c3 = bookshelf.getBookshelfTile(5,0);
            c4 = bookshelf.getBookshelfTile(5,4);

            if(c1.isTileEmpty() || c2.isTileEmpty() || c3.isTileEmpty() || c4.isTileEmpty()) return 0;
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(c1.getCard().sameType(c2.getCard()) && c3.getCard().sameType(c4.getCard()) && c2.getCard().sameType(c4.getCard())) return getScore();
        else return 0;
    }
}
