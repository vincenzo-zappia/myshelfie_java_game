/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

/*
 * Eight tiles of the same type.
 * There's no restriction about the position of these tiles.
 */

public class CommonGoal9 implements Goal{
    private static final int SCORE = 1; //TODO:inserire valore del goal

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int[][] x = bs.getColorMatrix();

        for(int i = 0; i < 6; i++){       //these two for cycles check every cell of the matrix
            for(int j = 0; j < 5; j++){
                if(x[i][j] != UNAVAILABLE){  //check single color per cycle
                    for(int k = 0; k < 6; k++){
                        for(int l = 0; l < 5; l++)if(x[k][l] != UNAVAILABLE && k!=i && l!=j && x[i][j] == x[k][l])tmp++;
                    }
                    if(tmp >= 8) return SCORE; //if the algorithm find 8 identical tiles return the score
                    tmp=0;
                }
            }
        }
        return 0;
    }
}
