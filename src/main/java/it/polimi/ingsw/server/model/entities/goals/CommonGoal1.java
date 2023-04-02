package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;

public class CommonGoal1 implements Goal{
    private static final int SCORE = 0; //TODO: inserire valore del goal
    @Override
    public int checkGoal(Bookshelf bs) {
        //equal square card
        int tmp=0;
        try {
            for(int i = 0; i < 5 ; i++){  //search from row 0 to row-1
                for(int j = 0; j < 4; j++){ //search from column 0 to column-1
                    if(!bs.getCell(i,j).isCellEmpty()){  //check the cell if it is empty or not
                        if(bs.getCell(i,j).getCard().equals(bs.getCell(i,j+1).getCard())     //if !empty search the 2x2 matrix
                           && bs.getCell(i,j).getCard().equals(bs.getCell(i+1,j).getCard())
                           && bs.getCell(i,j).getCard().equals(bs.getCell(i+1,j+1).getCard())){
                            tmp++;  //when one found increment tmp
                        }
                    }
                }
            }
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(tmp>=2) return SCORE;
        else return 0;
    }
}
