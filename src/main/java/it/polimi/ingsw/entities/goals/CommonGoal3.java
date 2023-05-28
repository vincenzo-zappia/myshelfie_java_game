package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.CardType;

/**
 * Four groups each containing at least 4 tiles of the same types (not necessarily in the depicted shape).
 * The tiles of one group can be different from those of another group
 */
public class CommonGoal3 extends CommonGoal implements Goal{

    //region CONSTRUCTOR
    public CommonGoal3() {
        super("Four groups each containing at least 4 tiles of the same types (not necessarily in the depicted shape).\n" +
                "The tiles of one group can be different from those of another group.", "cg3.jpg");
    }
    //endregion

    //region METHODS
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int groupNum = 0;

        //Calculating the number of groups of four cards of the same type for each type
        for(int i = 0; i < CardType.values().length; i++){
            groupNum += fourGroups(bookshelf.getBookshelfColors(), i);
        }

        //Scoring the common goal only if the groups are more than four
        if(groupNum >= 4) return getScore();
        return 0;
    }

    /**
     * Calculates the number of groups of four cards of the same type
     * @param matrix color abstracted bookshelf
     * @param codifiedType int representing the card type
     * @return the number of groups of four cards of the same type
     */
    private int fourGroups(int[][] matrix, int codifiedType){
        int four = 0;

        //Defining bookshelf dimensions
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols]; //Keeps track of already visited tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                //Checking whether a tile was already visited or has no type or is of the same type
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE && matrix[i][j] == codifiedType) {

                    //Incrementing the count of groups of four cards of the same type
                    four += Math.floorDiv(findAdjacent(matrix, visited, i, j, codifiedType), 4);
                }
            }
        }
        return four;
    }
    //endregion

}
