/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */
package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/**
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

        int tmp=0;
        int[][] x = bs.getBookshelfColors();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (x[i][j] != UNAVAILABLE) {
                    if (j < 4 && x[i][j] == x[i][j + 1]) {  // Check element to the right
                        x[i][j] = UNAVAILABLE;
                        x[i][j + 1] = UNAVAILABLE;
                        tmp++;
                    }
                    if (i < 5 && x[i][j] == x[i + 1][j]) {  // Check element below
                        x[i][j] = UNAVAILABLE;
                        x[i + 1][j] = UNAVAILABLE;
                        tmp++;
                    }
                }
            }
        }

        if(tmp>=6) return getScore();
        return 0;
    }

}
