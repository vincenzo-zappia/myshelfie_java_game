package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.CardType;

import static it.polimi.ingsw.entities.goals.CommonGoal.findAdjacent;

/**
 * The always present game common goal (the one on the board)
 */
public class CommonGoal0 implements Goal{

    //region METHODS
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int points = 0;

        //Calculating the points scored relatively to the biggest group of cards of the same type for each type
        for(int i = 0; i < CardType.values().length; i++){
            int maxGroupType = maxGroupType(bookshelf.getBookshelfColors(), i);
            if (maxGroupType == 3) points += 2;
            else if (maxGroupType == 4) points += 3;
            else if (maxGroupType == 5) points += 5;
            else if (maxGroupType >= 6) points += 8;
        }

        return points;
    }

    /**
     * Calculates the biggest group of cards of the same type
     * @param matrix type abstracted bookshelf
     * @param codifiedType int representing the card type
     * @return the size of the biggest group of cards of the same type
     */
    private int maxGroupType(int[][] matrix, int codifiedType) {
        int max = 0;

        //Defining bookshelf dimensions
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols]; //Keeps track of already visited tiles

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                //Checking whether a tile was already visited or has no type or is of the same type
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE && matrix[i][j] == codifiedType) {
                    int value = matrix[i][j];

                    //Choosing the biggest dimension between the previously calculated group and the current one
                    max = Math.max(max, findAdjacent(matrix, visited, i, j, value));
                }
            }
        }
        return max;
    }
    //endregion

}