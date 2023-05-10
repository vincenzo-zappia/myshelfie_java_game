package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.CardType;

/*
 * Five tiles of the same type forming a diagonal.
 */

public class CommonGoal11 extends CommonGoal implements Goal{
    private static final int[][] corners = {{0,0}, {0,4}, {5,0}, {5,4}};

    public CommonGoal11() {
        super("Five tiles of the same type forming a diagonal.");
    }

    private boolean findDiagonalWithSameColor(int row, int column, Bookshelf b){
        int mRow = 0, mColumn = 0;
        CardType compareType;

        if(row == 0) mRow = 1;
        else if (row == 5) mRow = -1;
        if (column == 0) mColumn = 1;
        else if (column == 4) mColumn = -1;


        try {
            compareType = b.getCell(row, column).getCard().getType();
        for(int i=0; i<5; i++) {
                CardType type = b.getCell(row+(i*mRow),column+(i+mColumn)).getCard().getType();
                if (!(type == compareType)) return false;
            }
            }catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        for(int i=0; i<4; i++) if (findDiagonalWithSameColor(corners[i][0], corners[i][1], bookshelf)) return getScore();
        return 0;
    }
}
