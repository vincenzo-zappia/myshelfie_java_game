package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.util.CardType;

import java.util.HashMap;


/*
 * Questo CommonGoal si riferisce ai Goals presente sulla Board
 */

public class CommonGoal0 implements Goal{
    private int[][] mColor = new int[6][5];
    private final int[] scores;

    public void setColorMatrix(int[][] matrix){
        mColor = matrix;
    }

    public CommonGoal0() {
        scores = new int[]{2,3,5,8};
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {

        int partial = 0;
        for(CardType type: CardType.values()){

        }
        return findLargestAdjacentGroup(mColor);
    }

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

}