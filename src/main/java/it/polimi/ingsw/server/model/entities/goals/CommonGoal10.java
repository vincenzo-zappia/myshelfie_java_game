package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.server.model.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;

/*
 * Five tiles of the same type forming an X.
 */

public class CommonGoal10 extends CommonGoal implements Goal{
    private boolean isX(int row, int column, Bookshelf b){
        Card c1, c2, c3, c4, c5;

        try {
            c1 = b.getCell(row, column).getCard();
            c2 = b.getCell(row, column +2).getCard();
            c3 = b.getCell(row+1, column+1).getCard();
            c4 = b.getCell(row+2, column+2).getCard();
            c5 = b.getCell(row+2, column+2).getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        return (c1.sameType(c2) && c1.sameType(c3) && c1.sameType(c4) && c4.sameType(c5));

    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        for(int i = 0; (i+2)<6; i++){
            for (int j = 0; (j+2)<5; j++) if(isX(j, i, bookshelf)) return getScore();
        }
        return 0;
    }
}
