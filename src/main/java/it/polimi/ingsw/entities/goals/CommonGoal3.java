package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Cell;

public class CommonGoal3 implements Goal{

    private static final int SCORE = 0; //TODO:inserire valore del goal
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int tmp=0;
        int points=0;

        for(int i = 0; i < 5; i++){
            //TODO finire implementando metodo di vince
        }

        return SCORE;
    }
}
