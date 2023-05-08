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

    private static class TempCardType {
        private CardType type;
        private int availibility;
        public TempCardType(CardType type){
            availibility = 0;
            this.type=type;
        }
        public void notAvailable(){availibility=UNAVAILABLE;}
    }

    /**
     * Algorithm that search a single sequence of adjacent
     * with the same tile
     * @param matrix used to search the sequences
     * @return true if one is found
     */
    private boolean searchSeq(TempCardType[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].availibility != UNAVAILABLE){
                    //controllo orizzontale
                    if (j <= matrix[i].length - 4) {
                        if (matrix[i][j].type.equals(matrix[i][j+1].type) && matrix[i][j+1].type.equals(matrix[i][j+2].type) && matrix[i][j+2].type.equals(matrix[i][j+3].type)) {
                            matrix[i][j].notAvailable();
                            matrix[i][j+1].notAvailable();
                            matrix[i][j+2].notAvailable();
                            matrix[i][j+3].notAvailable();
                            return true;
                        }
                    }
                    //controllo verticale
                    if (i <= matrix.length - 4) {
                        if (matrix[i][j].equals(matrix[i+1][j]) && matrix[i+1][j].equals(matrix[i+2][j]) && matrix[i+2][j].equals(matrix[i+3][j])) {
                            matrix[i][j].notAvailable();
                            matrix[i+1][j].notAvailable();
                            matrix[i+2][j].notAvailable();
                            matrix[i+3][j].notAvailable();
                            return true;
                        }
                    }
                    //controllo diagonale in basso a destra
                    if (i <= matrix.length - 4 && j <= matrix[i].length - 4) {
                        if (matrix[i][j].equals(matrix[i+1][j+1]) && matrix[i+1][j+1].equals(matrix[i+2][j+2]) && matrix[i+2][j+2].equals(matrix[i+3][j+3])) {
                            matrix[i][j].notAvailable();
                            matrix[i+1][j+1].notAvailable();
                            matrix[i+2][j+2].notAvailable();
                            matrix[i+3][j+3].notAvailable();
                            return true;
                        }
                    }
                    //controllo diagonale in basso a sinistra
                    if (i <= matrix.length - 4 && j >= 3) {
                        if (matrix[i][j].equals(matrix[i+1][j-1]) && matrix[i+1][j-1].equals(matrix[i+2][j-2]) && matrix[i+2][j-2].equals(matrix[i+3][j-3])) {
                            matrix[i][j].notAvailable();
                            matrix[i+1][j-1].notAvailable();
                            matrix[i+2][j-2].notAvailable();
                            matrix[i+3][j-3].notAvailable();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //TODO: migliorare algoritmo searchSeq (mancano alcune forme)

    /**
     * Convert a CardType into a TempCardType matrix;
     * @param x is CardType matrix from Bookshelf
     * @return the converted bidimensional array
     */
    private TempCardType[][] makeMatrix(CardType[][] x){
        TempCardType[][] m = new TempCardType[6][5];

        for(int i = 0; i<6; i++){
            for(int j=0; j<5; j++){
                m[i][j].type=x[i][j];
            }
        }
        return m;
    }

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp=0;
        int result = 0;
        TempCardType[][] m = makeMatrix(bs.getMatrixTypes());

        do{
            if(searchSeq(m)){
                result++;
            }
            tmp++;
        }while(tmp<4);
        if(result>=4) return getScore();
        return 0;
    }
}
