package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal7 implements Goal{
    private static final int SCORE = 1; //TODO:inserire valore del goal

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp;
        int cntr=0;

        try {
            for(int i = 0; i < 6; i++){
                tmp=0;
                for(int j = 0; j < 5; j++){
                    if(!bs.getCell(j,i).isCellEmpty() && !bs.getCell(i, 0).getCard().equals(bs.getCell(i,j).getCard())){
                        tmp++;
                    }
                }
                if(tmp<=3)cntr++;
            }
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(cntr>=4) return SCORE;
        return 0;
    }
}
