package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal3 implements Goal{

    private static final int SCORE = 1; //TODO:inserire valore del goal
    private static final int UNAVAILABLE= 104; //DEBUG use
    private int[][] x = new int[6][5];

    //TODO same of commonGoal1
    public void matrixExtractor(Bookshelf bs){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++) {
                try {
                    if(!bs.getCell(i,j).isCellEmpty())x[i][j] = bs.getCell(i,j).getCard().getColor();
                } catch (CellGetCardException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int points=0;
        matrixExtractor(bs);

        for(int j = 0; j < 5; j++){
            for(int i = 0; i < 3; i++){
                if (!bs.getCell(i, j).isCellEmpty()) {
                    if(x[i][j] == x[i+1][j] && x[i+1][j] == x[i+2][j] && x[i+2][j] == x[i+3][j]){
                        tmp++;
                    }
                }
            }
        }

        if(tmp>=4) return SCORE;
        return 0;
    }
}
