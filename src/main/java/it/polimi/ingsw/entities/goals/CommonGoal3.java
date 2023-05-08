/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: da finire implementando algoritmo per ricercare sequenza di carte
 */

package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.util.CardType;


/*
 * Four groups each containing at least 4 tiles of the same types (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group.
 */

public class CommonGoal3 extends CommonGoal implements Goal{

    /**
     * Algorithm that search a single sequence of adjacent
     * with the same tile
     * @param matrix used to search the sequences
     * @return true if one is found
     */
    private boolean searchSeq(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] != UNAVAILABLE){
                    //controllo orizzontale
                    if (j <= matrix[i].length - 4) {
                        if (matrix[i][j] == matrix[i][j+1] && matrix[i][j+1] == matrix[i][j+2] && matrix[i][j+2] == matrix[i][j+3]) {
                            matrix[i][j] = UNAVAILABLE;
                            matrix[i][j+1] = UNAVAILABLE;
                            matrix[i][j+2] = UNAVAILABLE;
                            matrix[i][j+3] = UNAVAILABLE;
                            return true;
                        }
                    }
                    //controllo verticale
                    if (i <= matrix.length - 4) {
                        if (matrix[i][j] == matrix[i+1][j] && matrix[i+1][j] == matrix[i+2][j] && matrix[i+2][j] == matrix[i+3][j]) {
                            matrix[i][j] = UNAVAILABLE;
                            matrix[i+1][j] = UNAVAILABLE;
                            matrix[i+2][j] = UNAVAILABLE;
                            matrix[i+3][j] = UNAVAILABLE;
                            return true;
                        }
                    }
                    //controllo diagonale in basso a destra
                    if (i <= matrix.length - 4 && j <= matrix[i].length - 4) {
                        if (matrix[i][j] == matrix[i+1][j+1] && matrix[i+1][j+1] == matrix[i+2][j+2] && matrix[i+2][j+2] == matrix[i+3][j+3]) {
                            matrix[i][j] = UNAVAILABLE;
                            matrix[i+1][j+1] = UNAVAILABLE;
                            matrix[i+2][j+2] = UNAVAILABLE;
                            matrix[i+3][j+3] = UNAVAILABLE;
                            return true;
                        }
                    }
                    //controllo diagonale in basso a sinistra
                    if (i <= matrix.length - 4 && j >= 3) {
                        if (matrix[i][j] == matrix[i+1][j-1] && matrix[i+1][j-1] == matrix[i+2][j-2] && matrix[i+2][j-2] == matrix[i+3][j-3]) {
                            matrix[i][j] = UNAVAILABLE;
                            matrix[i+1][j-1] = UNAVAILABLE;
                            matrix[i+2][j-2] = UNAVAILABLE;
                            matrix[i+3][j-3] = UNAVAILABLE;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //TODO: migliorare algoritmo searchSeq (mancano alcune forme e.g. "L")

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int result = 0;
        int[][] m = bs.getMatrixColors();

        do{
            if(searchSeq(m))result++;
            tmp++;
        }while(tmp<4);
        if(result>=4) return getScore();
        return 0;
    }
}
