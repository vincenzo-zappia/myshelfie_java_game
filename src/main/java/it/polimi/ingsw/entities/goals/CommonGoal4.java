/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */
package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/*
 * Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another groups.
 */

public class CommonGoal4 extends CommonGoal implements Goal{

    public CommonGoal4() {
        super("Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).\n" +
                "The tiles of one group can be different from those of another groups.", "cg4.jpg");
    }

    @Override
    public int checkGoal(Bookshelf bs) {

        //Verifico che il goal non sia gia stato preso //todo: tradurre
        if(isReaced()) return 0;

        int tmp=0;
        int[][] x = bs.getBookshelfColors();

        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (x[i][j] != UNAVAILABLE) {
                    if (i == 0 && j == 0) {               //CASE 1: [0][0]
                        if (x[0][0] == x[0][1]) {     //check at dx
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
                        if (x[i][0] == x[i][1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[i][0] == x[i + 1][0]) {
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
                        if (x[0][j] == x[0][j + 1]) {
                            x[i][j] = UNAVAILABLE;
                            x[i][j + 1] = UNAVAILABLE;
                            tmp++;
                        }
                        if (x[0][j] == x[i + 1][j]) {
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
        if(tmp>=6){
            goalReaced();
            return getScore();
        }
        return 0;
    }
}
