package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.CardType;

import java.util.HashSet;


/*
 * Two columns each formed by 6 different type of tiles.
 */

public class CommonGoal2 extends CommonGoal implements Goal {
    private int[][] matrix;

    private boolean allColorsDifferent(int column){
        HashSet<CardType> scannedTypes = new HashSet<>();
        CardType[] types = CardType.values();

        for (int[] ints : matrix) {
            if (ints[column] == Goal.UNAVAILABLE) return false;
            int index = ints[column];
            if (scannedTypes.contains(types[index])) return false;
            else scannedTypes.add(types[index]);
        }
        return true;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        System.out.println("INFO: checkGoal");
        matrix = bookshelf.getMatrixColors();
        System.out.println("bp1");
        int count=0;
        for(int i=0; i<5; i++){
            if (bookshelf.cardsInColumn(i) == 6) continue;
            if (allColorsDifferent(i)) count++;
        }
        //TODO: Vedere se deve essere strettamente uguale o almeno 2
        System.out.println("bp2");
        if (count>=2) {
            System.out.println("INFO: getsocore");
            return getScore();
        }
        else{
            System.out.println("INFO: zero");
            return 0;
        }
    }
}
