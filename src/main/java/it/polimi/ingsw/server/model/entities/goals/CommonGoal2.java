package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;


/*
 * Two columns each formed by 6 different type of tiles.
 */

public class CommonGoal2 extends CommonGoal implements Goal{
    private boolean allColorsDifferent(int[] colors){
        for (int i=0; i<5; i++) for (int j=i+1; j<6; j++) if (colors[i] == colors[j]) return false;
        return true;
    }
    private int[] getColumnColors(int column, Bookshelf b){
        int[] colors = new int[6];
        for(int i=0; i<6; i++) {
            try {
                if (!b.getCell(i, column).isCellEmpty()) colors[i] = b.getCell(i, column).getCard().getColor();
            } catch (CellGetCardException e) {
                throw new RuntimeException(e);
            }
        }
        return colors;
    }
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int count=0;
        for(int i=0; i<5; i++){
            if (allColorsDifferent(getColumnColors(i, bookshelf))) count++;
        }
        //TODO: Vedere se deve essere strettamente uguale o almeno 2
        if (count>=2) return getScore();
        else return 0;
    }
}
