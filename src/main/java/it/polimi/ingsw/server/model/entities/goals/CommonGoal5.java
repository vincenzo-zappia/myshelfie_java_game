package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal5 implements Goal{
    private static final int SCORE = 1; //TODO:inserire valore del goal

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp;
        int cntr=0;

        try {
            for(int i = 0; i < 5; i++){
                tmp=0;
                for(int j = 0; j < 6; j++){
                    if(!bs.getCell(j,i).isCellEmpty() && !bs.getCell(0, i).getCard().sameColor(bs.getCell(j,i).getCard())){
                        tmp++;
                    }
                }
                if(tmp<=3)cntr++;
            }
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(cntr>=3) return SCORE;
        return 0;
    }
}
