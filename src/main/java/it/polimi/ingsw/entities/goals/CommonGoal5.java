package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;

/*
 * Three columns each cformed by 6 tiles of maximum three different types.
 * One column can show the same or a different combination of another column.
 */

public class CommonGoal5 extends CommonGoal implements Goal{

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp;
        int cntr=0;

        try {
            for(int i = 0; i < 5; i++){
                tmp=0;
                for(int j = 0; j < 6; j++){
                    if(!bs.getCell(j,i).isCellEmpty() && !bs.getCell(0, i).getCard().sameType(bs.getCell(j,i).getCard())){
                        tmp++;
                    }
                }
                if(tmp<=3)cntr++;
            }
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(cntr>=3) return getScore();
        return 0;
    }
}
