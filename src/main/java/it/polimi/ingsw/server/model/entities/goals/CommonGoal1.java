package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal1 implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal

    @Override
    public int checkGoal(Bookshelf bs) {
        //equal square card
        int tmp=0;
        int[][] x = bs.getColorMatrix();

        for(int i = 0; i < 5 ; i++){  //search from row 0 to row-1
            //TODO verificare che funzioni senza controllo in questa sezione
            for(int j = 0; j < 4; j++){ //search from column 0 to column-1
                if(x[i][j] != UNAVAILABLE){  //check the cell if it is empty or not
                    if(x[i][j] == x[i][j+1] && x[i][j] == x[i+1][j] && x[i][j] == x[i+1][j+1]){
                        tmp++;  //when one found increment tmp
                        x[i][j] = UNAVAILABLE;
                        x[i][j+1] = UNAVAILABLE;
                        x[i+1][j] = UNAVAILABLE;
                        x[i+1][j+1] = UNAVAILABLE;
                    }
                }
            }
        }

        if(tmp>=2) return SCORE;
        else return 0;
    }
}
