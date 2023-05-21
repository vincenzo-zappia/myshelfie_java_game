package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.GetCardException;
import it.polimi.ingsw.entities.util.Tile;

/*
 * Five tiles of the same type forming an X.
 */

public class CommonGoal10 extends CommonGoal implements Goal{
    private Bookshelf b;

    public CommonGoal10() {
        super("Five tiles of the same type forming an X.", "cg10.jpg");
    }

    private boolean isX(int row, int column){
        Tile[] c = new Tile[5];

        try {
            c[0] = b.getBookshelfTile(row, column);
            c[1] = b.getBookshelfTile(row, column +2);
            c[2] = b.getBookshelfTile(row+1, column+1);
            c[3] = b.getBookshelfTile(row+2, column);
            c[4] = b.getBookshelfTile(row+2, column+2);
            if(existEmpty(c)) return false;
        } catch (GetCardException e) {
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
