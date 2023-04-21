package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;

/*
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 */

public class CommonGoal6 implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal
    private boolean allColorsDifferent(int[] colors){
        for (int i=0; i<4; i++) for (int j=i+1; j<5; j++) if (colors[i] == colors[j]) return false;
        return true;
    }
    private int[] getRowColors(int row, Bookshelf b){
        int[] colors = new int[5];
        for(int i=0; i<5; i++) {
            try {
                colors[i] = b.getCell(row, i).getCard().getColor();
            } catch (CellGetCardException e) {
                throw new RuntimeException(e);
            }
        }
        return colors;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int count=0;
        for(int i=0; i<6; i++){
            if (allColorsDifferent(getRowColors(i, bookshelf))) count++;
        }
        //TODO: Vedere se deve essere strettamente uguale o almeno 2
        if(count>=2) return SCORE;
        else return 0;
    }
}
