package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

/*
 * Questo CommonGoal si riferisce ai Goals presente sulla Board
 */

public class CommonGoal0 implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal

//    private int countAdjacent(Bookshelf b){
//        int[][] cMatrix = b.getColorMatrix();
//
//        while ()
//
//    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return 0;
    }
}
