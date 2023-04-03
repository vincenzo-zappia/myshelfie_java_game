package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal4 implements Goal{
    private static final int SCORE = 1; //TODO:inserire valore del goal
    private int[][] x = new int[6][5];

    public void matrixExtractor(Bookshelf bs){  //using this method to extract colors from
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

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(!bs.getCell(i,j).isCellEmpty()){
                    if(x[i][j] == x[i+1][j]){   //check if the actual cell card color is
                        tmp++;                  //the same of the cell below
                    }
                }
            }
        }

        if(tmp>=6) return SCORE;
        return 0;
    }
}
