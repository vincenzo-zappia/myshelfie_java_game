/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.CardType;

import java.util.Arrays;
import java.util.stream.IntStream;

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
    public int checkGoal(Bookshelf bookshelf) {

        //Checking if there are more than eight cards of the same type for at least one type
        for(int i = 0; i < CardType.values().length; i++){
            if(typeNumber(bookshelf.getBookshelfColors(), i) >= 8) return getScore();
        }

        return 0;
    }

    /**
     * Calculates the number of cards of the same type
     * @param matrix color abstracted bookshelf
     * @param codifiedType int representing the card type
     * @return the number of cards of the same type
     */
    private int typeNumber(int[][] matrix, int codifiedType){
        return Arrays.stream(matrix).mapToInt(ints -> (int) IntStream.range(0, matrix[0].length).filter(j -> ints[j] == codifiedType).count()).sum();
    }

}
