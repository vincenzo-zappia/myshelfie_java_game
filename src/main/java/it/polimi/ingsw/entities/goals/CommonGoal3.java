/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: da finire implementando algoritmo per ricercare sequenza di carte
 */

package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/*
 * Four groups each containing at least 4 tiles of the same types (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 */

public class CommonGoal3 extends CommonGoal implements Goal{

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int[][] x = bs.getMatrixColors();

        //TODO algoritmo per trovare la sequenza di carte della figura 3.

        if(tmp>=4) return getScore();
        return 0;
    }
}
