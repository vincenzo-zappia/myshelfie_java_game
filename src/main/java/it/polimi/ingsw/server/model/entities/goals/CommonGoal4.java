package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal4 implements Goal{
    private static final int SCORE = 1; //TODO:inserire valore del goal

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int[][] x = bs.getColorMatrix();

        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (x[i][j] != UNAVAILABLE) {
                    if (i == 0 && j == 0) {               //CASE 1: [0][0]
                        if (x[i][j] == x[i][j + 1]) {     //check at dx
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i + 1][j]) {     //check below
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if (i == 0 && j == 4) {               //CASE 2: [0][4]
                        if (x[i][j] == x[i][j - 1]) {     //check at sx
                            x[i][j] = UNAVAILABLE;
                            x[i][j - 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i + 1][j]) {     //check below
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if (i == 5 && j == 0) {              //CASE 3: [5][0]
                        if (x[i][j] == x[i][j + 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i - 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i - 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if (i > 0 && i < 5 && j > 0 && j < 4) { //CASE 4: check colors in the matrix (not borders)
                        if (x[i][j] == x[i][j + 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i + 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i][j - 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j - 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i - 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i - 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if(i > 0 && i < 5 && j == 0){         //CASE 5: [x][0/1] check rows in the first or second column
                        if (x[i][j] == x[i][j + 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i + 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i - 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i - 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if(i > 0 && i < 5 && j == 4){         //CASE 6: [x][4] check rows in the last column
                        if (x[i][j] == x[i][j - 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i + 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i - 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i - 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if (i == 0 && j > 0 && j < 4) {       //CASE 7
                        if (x[i][j] == x[i][j + 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i + 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i][j - 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j - 1] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                    if (i == 5 && j > 0 && j < 4) {       //CASE 8
                        if (x[i][j] == x[i][j + 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i - 1][j]) {
                            x[i][j] = UNAVAILABLE;
                            x[i + 1][j] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][j] == x[i][j - 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j - 1] = UNAVAILABLE;
                            tmp++;
                        }
                    }
                }
            }
        }
        if(tmp>=6) return SCORE;
        return 0;
    }
}
