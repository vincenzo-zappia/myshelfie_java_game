package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;

import java.util.ArrayList;

public class CommonGoal2 implements Goal{
    private static final int SCORE = 0; //TODO: inserire valore del goal
    private boolean allColorsDifferent(int[] colors){
        for (int i=0; i<5; i++) for (int j=i+1; j<6; j++) if (colors[i] == colors[j]) return false;
        return true;
    }
    private int[] getColumnColors(int column, Bookshelf b){
        int[] colors = new int[6];
        for(int i=0; i<6; i++) {
            try {
                colors[i] = b.getCell(i, column).getCard().getColor();
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
        if (count>=2) return SCORE;
        else return 0;
    }
}
