package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal9 implements Goal{
    private static final int SCORE = 1; //TODO:inserire valore del goal

    @Override
    public int checkGoal(Bookshelf bs) {
        boolean sentinel = false;


        /*
         * 0 1 0 1 0
         * 0 0 0 0 0     può scorrere di uno verso basso/alto
         * 1 0 1 0 1     a seconda dell'implementazione
         * 0 0 0 0 0
         * 1 0 1 0 1
         * 0 0 0 0 0
         */

        return 0;
    }
}
