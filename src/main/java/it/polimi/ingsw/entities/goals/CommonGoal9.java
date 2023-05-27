/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/**
 * Eight tiles of the same type.
 * There's no restriction about the position of these tiles.
 */
public class CommonGoal9 extends CommonGoal implements Goal{

    public CommonGoal9() {
        super("Eight tiles of the same type.\n" +
                "There's no restriction about the position of these tiles.", "cg9.jpg");
    }

    @Override
    public int checkGoal(Bookshelf bs) {

        int tmp=0;
        int[][] x = bs.getBookshelfColors();

        for(int i = 0; i < 6; i++){       //these two for cycles check every cell of the matrix
            for(int j = 0; j < 5; j++){
                if(x[i][j] != UNAVAILABLE){  //check single color per cycle
                    for(int k = 0; k < 6; k++){
                        for(int l = 0; l < 5; l++)if(x[k][l] != UNAVAILABLE && k!=i && l!=j && x[i][j] == x[k][l])tmp++;
                    }
                    if(tmp >= 8) return getScore(); //if the algorithm find 8 identical tiles return the score
                    tmp=0;
                }
            }
        }
        return 0;
    }

}
