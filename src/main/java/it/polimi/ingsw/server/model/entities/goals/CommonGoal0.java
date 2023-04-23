package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

import java.util.HashSet;
import java.util.Set;

/*
 * Questo CommonGoal si riferisce ai Goals presente sulla Board
 */

public class CommonGoal0 extends CommonGoal implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal
    private int[][] mColor = new int[6][5];

    private int starterAdjacent(int row, int column){
        HashSet<String> hs = new HashSet<String>();
        hs.add(row + ";" + column);
        adjacent(row, column, hs);
        return hs.size();
    }

    private void adjacent(int row, int column, HashSet<String> hs){
        //Verifica colonne
        if(column < 4) if(mColor[row][column] == mColor[row][column+1]) adjAdder(row, column+1, hs);
        //if(column > 0) if(mColor[row][column] == mColor[row][column-1]) adjAdder(row, column-1, hs);

        //Verifica righe
        if(row < 5) if(mColor[row][column] == mColor[row+1][column]) adjAdder(row+1, column, hs);
        //if(row > 0) if(mColor[row][column] == mColor[row-1][column]) adjAdder(row-1, column, hs);
    }

    private void adjAdder(int row, int column, HashSet<String> hs){
        hs.add(row + ";" + column);
        adjacent(row, column, hs);
    }

    public void setColorMatrix(int[][] matrix){
        mColor = matrix;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return starterAdjacent(3,1);
    }
}
