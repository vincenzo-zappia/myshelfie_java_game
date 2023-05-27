package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.Tile;

/**
 * Five tiles of the same type forming a diagonal
 */
public class CommonGoal11 extends CommonGoal implements Goal{

    //region ATTRIBUTES
    private static final int[][] corners = {{0,0}, {0,4}, {5,0}, {5,4}};
    //endregion

    //region CONSTRUCTOR
    public CommonGoal11() {
        super("Five tiles of the same type forming a diagonal.", "cg11.jpg");
    }
    //endregion

    //region METHODS
    @Override
    public int checkGoal(Bookshelf bookshelf) {

        for(int i=0; i<4; i++) if (findDiagonalWithSameColor(corners[i][0], corners[i][1], bookshelf)) return getScore();
        return 0;
    }

    private boolean findDiagonalWithSameColor(int row, int column, Bookshelf b){
        int mRow = 0, mColumn = 0;

        if(row == 0) mRow = 1;
        else if (row == 5) mRow = -1;
        if (column == 0) mColumn = 1;
        else if (column == 4) mColumn = -1;


            Tile[] list = new Tile[5];
            for(int i=0; i<5; i++) {
                list[i] = b.getBookshelfTile(row+(i*mRow),column+(i*mColumn));
                if (list[i].isTileEmpty()) return false;
            }

        return allSameType(list);
    }
    //endregion

}
