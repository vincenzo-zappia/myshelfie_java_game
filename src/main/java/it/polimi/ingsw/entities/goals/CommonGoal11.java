package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;

/*
 * Five tiles of the same type forming a diagonal.
 */

public class CommonGoal11 extends CommonGoal implements Goal{
    private static final int[][] corners = {{0,0}, {0,4}, {5,0}, {5,4}};
    private boolean findDiagonalWithSameColor(int row, int column, Bookshelf b){
        int mRow = 0, mColumn = 0, compareColor;

        if(row == 0) mRow = 1;
        else if (row == 5) mRow = -1;
        if (column == 0) mColumn = 1;
        else if (column == 4) mColumn = -1;


        try {
            compareColor = b.getCell(row, column).getCard().getType().ordinal();
        for(int i=0; i<5; i++) {
                int color = b.getCell(row+(i*mRow),column+(i+mColumn)).getCard().getType().ordinal();
                if (!(color == compareColor)) return false;
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
