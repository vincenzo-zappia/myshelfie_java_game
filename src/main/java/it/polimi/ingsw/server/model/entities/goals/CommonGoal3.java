/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: da finire implementando algoritmo per ricercare sequenza di carte
 */
package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.model.entities.Bookshelf;

public class CommonGoal3 implements Goal{

    private static final int SCORE = 1; //TODO:inserire valore del goal
    private static final int UNAVAILABLE= 104; //DEBUG use



    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int x[][] = bs.getColorMatrix();

        //TODO algoritmo per trovare la sequenza di carte della figura 3.

        if(tmp>=4) return SCORE;
        return 0;
    }
}
