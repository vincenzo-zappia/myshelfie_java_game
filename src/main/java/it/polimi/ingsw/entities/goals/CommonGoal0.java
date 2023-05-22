package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.CardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * Questo CommonGoal si riferisce ai Goals presente sulla Board
 */
public class CommonGoal0 implements Goal{

    //TODO: Vincè controlla se va bene
    //private int[][] mColor = new int[6][5];
    //private final int[] scores;
   //private int partial;
    /*
    public void setColorMatrix(int[][] matrix){
        mColor = matrix;
    }
    */
    public CommonGoal0() {
        //scores = new int[]{0,3,5,8};
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        /*
            partial = 0;
            for(CardType type: CardType.values()){...}

            return findLargestAdjacentGroup(mColor);
        */

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
                if (!visited[i][j]) {
                    int value = matrix[i][j];
                    num.add(findAdjacent(matrix, visited, i, j, value));
                }
            }
        }
        return Collections.max(num); //TODO: contare solo gruppo più grande o restituire punteggio per tutti gruppi presenti?
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

    //TODO: se precedenti metodi funzionano, rimuovere sottostanti
    /*
    private static int findLargestAdjacentGroup(int[][] matrix) {
        int largestGroupSize = 0;

            // Visita ogni elemento della matrice come un nodo in un grafo
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int currentGroupSize = visitNode(matrix, i, j, matrix[i][j], new boolean[matrix.length][matrix[0].length]);
                largestGroupSize = Math.max(largestGroupSize, currentGroupSize);
            }
        }

        return largestGroupSize;
    }



    // Visita un nodo e tutti i suoi nodi adiacenti che hanno lo stesso valore
    private static int visitNode(int[][] matrix, int i, int j, int value, boolean[][] visited) {
        // Verifica se il nodo è già stato visitato o se il valore non corrisponde
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || visited[i][j] || matrix[i][j] != value || matrix[i][j] == Goal.UNAVAILABLE) {
            return 0;
        }

        visited[i][j] = true;

        // Visita tutti i nodi adiacenti che hanno lo stesso valore
        int size = 1;
        size += visitNode(matrix, i + 1, j, value, visited);
        size += visitNode(matrix, i - 1, j, value, visited);
        size += visitNode(matrix, i, j + 1, value, visited);
        size += visitNode(matrix, i, j - 1, value, visited);

        return size;
    }
*/
}