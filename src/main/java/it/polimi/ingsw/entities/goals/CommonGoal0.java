package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.CardType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * Questo CommonGoal si riferisce ai Goals presenti sulla Board
 */
public class CommonGoal0 implements Goal{

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return searchGroups(bookshelf.getBookshelfColors());
    }

    /**
     * Methods that search the max player's group of tiles
     * @param matrix bookshelf colors
     * @return group's size
     */
    private int searchGroups(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        ArrayList<Integer> num = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE) {
                    int value = matrix[i][j];
                    num.add(findAdjacent(matrix, visited, i, j, value));
                }
            }
        }
        int res = Collections.max(num);
        if(res>1)return res;
        return 0;
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

}