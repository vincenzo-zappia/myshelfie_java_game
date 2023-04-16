package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

/*
 * Questo CommonGoal si riferisce ai Goals presente sulla Board
 */

public class CommonGoal0 implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal
    private int[][] mColor = new int[6][5];

    private int countMaxAdjacent(int row, int column){
        int addRow=1, addColumn=1, count=0;

        do{
            if(row + addRow > 5 || column > 4) break;
            while (verifyOrizontal(row, column, addColumn) == 1) count++;
            while (verifyOrizontal(row, column, -addColumn) == 1) count++;

            count++;
            addColumn++;
            addRow++;
        } while(mColor[row][column] == mColor[row+addRow][column]);

            return count;
    }

    public int verifyOrizontal(int row, int column, int offset){
        if ((row > 5) || (column + offset > 4)) return 0;

        if (mColor[row][column] == mColor[row][column+offset]) return 1;
        else return 0;
    }

//    public int verifyLeft(int row, int column, int offset){
//
//    }

    private int countAdjacent(int row, int column){

        int count =1;
        if (row < 5) if(mColor[row][column] == mColor[row+1][column]) count += countAdjacent(row+1, column);
        if (column < 4) if(mColor[row][column] == mColor[row][column+1]) count += countAdjacent(row, column+1);
        return count;
    }

    public void setColorMatrix(int[][] matrix){
        mColor = matrix;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return countAdjacent(4,0);
    }
}
