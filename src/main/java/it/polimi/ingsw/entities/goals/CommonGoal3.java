/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 2/04/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: da finire implementando algoritmo per ricercare sequenza di carte
 */

package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

import java.util.ArrayList;
import java.util.List;

public class CommonGoal3 extends CommonGoal implements Goal{

    public CommonGoal3() {
        super("Four groups each containing at least 4 tiles of the same types (not necessarily in the depicted shape).\n" +
                "The tiles of one group can be different from those of another group.", "cg3.jpg");
    }

    /**
     * Algorithm that search a single sequence of adjacent
     * with the same tile
     * @param matrix used to search the sequences
     * @return true if one is found
     */
    private boolean searchSeq(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int groupCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    int value = matrix[i][j];
                    int groupSize = findAdjacent(matrix, visited, i, j, value);
                    if (groupSize >= 4) {
                        groupCount++;
                        if (groupCount >= 4) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check the adjacents tiles and count them
     * @param matrix int[][] of cards's types
     * @param visited matrix of already visited cards
     * @param row to check
     * @param col to check
     * @param value contained in tile
     * @return count of adjacent tiles group
     */
    private int findAdjacent(int[][] matrix, boolean[][] visited, int row, int col, int value) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col] || matrix[row][col] != value) {
            return 0;
        }

        visited[row][col] = true;

        int count = 1;
        count += findAdjacent(matrix, visited, row - 1, col, value); //Up
        count += findAdjacent(matrix, visited, row + 1, col, value); //Down
        count += findAdjacent(matrix, visited, row, col - 1, value); //Left
        count += findAdjacent(matrix, visited, row, col + 1, value); //Right

        return count;
    }

    @Override
    public int checkGoal(Bookshelf bs) {
        if(searchSeq(bs.getBookshelfColors())) return getScore();
        return 0;
    }
}
