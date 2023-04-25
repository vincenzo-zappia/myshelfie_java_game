package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

/*
 * Four lines each formed by 5 tiles of maximum three different types.
 * One line can show the same or a different combination of another line.
 */

public class CommonGoal7 extends CommonGoal implements Goal{

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp;
        int cntr=0;

        try {
            for(int i = 0; i < 6; i++){
                tmp=0;
                for(int j = 0; j < 5; j++){
                    if(!bs.getCell(j,i).isCellEmpty() && !bs.getCell(i, 0).getCard().sameType(bs.getCell(i,j).getCard())){
                        tmp++;
                    }
                }
                if(tmp<=3)cntr++;
            }
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(cntr>=4) return getScore();
        return 0;
    }
}
