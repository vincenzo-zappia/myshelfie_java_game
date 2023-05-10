package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.Cell;

/*
 * Five tiles of the same type forming an X.
 */

public class CommonGoal10 extends CommonGoal implements Goal{
    private Bookshelf b;

    public CommonGoal10() {
        super("Five tiles of the same type forming an X.");
    }

    private boolean isX(int row, int column){
        Cell[] c = new Cell[5];

        try {
            c[0] = b.getCell(row, column);
            c[1] = b.getCell(row, column +2);
            c[2] = b.getCell(row+1, column+1);
            c[3] = b.getCell(row+2, column);
            c[4] = b.getCell(row+2, column+2);
            if(existEmpty(c)) return false;
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        return sameTypes(c);

    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        b = bookshelf;
        for(int i = 0; i<4; i++){
            for (int j = 0; j<3; j++){
                if(isX(i, j)) return getScore();
            }
        }
        return 0;
    }
}
