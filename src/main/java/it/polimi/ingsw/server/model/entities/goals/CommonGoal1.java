package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Card;
import it.polimi.ingsw.server.model.entities.Cell;

public class CommonGoal1 implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal
    private int[][] x = new int[6][5];

    //TODO add this method in bookshelf class, and make checkGoal accept an int[][] like parameter.
    public void matrixExtractor(Bookshelf bs){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 5; j++) {
                try {
                    x[i][j] = bs.getCell(i,j).getCard().getColor();  //extract color from bookshelf
                } catch (CellGetCardException e) {                   // and save the value in matrix x[][]
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public int checkGoal(Bookshelf bs) {
        //equal square card
        int tmp=0;
        int tipo=0;
        matrixExtractor(bs);

        for(int i = 0; i < 5 ; i++){  //search from row 0 to row-1
            for(int j = 0; j < 4; j++){ //search from column 0 to column-1
                if(!bs.getCell(i,j).isCellEmpty()){  //check the cell if it is empty or not
                    if(tipo==0){
                        if(x[i][j] == x[i][j+1] && x[i][j] == x[i+1][j] && x[i][j] == x[i+1][j+1]){
                            tmp++;  //when one found increment tmp
                            tipo=x[i][j]; //used in second if to check that they are of the same type/color
                        }
                    }
                    if(tipo == x[i][j] && x[i][j] == x[i][j+1] && x[i][j] == x[i+1][j] && x[i][j] == x[i+1][j+1]){
                        tmp++;  //when one found increment tmp
                    }
                }
            }
        }

        if(tmp>=2) return SCORE;
        else return 0;
    }
}
