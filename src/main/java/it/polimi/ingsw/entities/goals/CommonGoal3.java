package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

public class CommonGoal3 extends CommonGoal implements Goal{

    int count;
    int[][] doublecheck;

    public CommonGoal3() {
        super("Four groups each containing at least 4 tiles of the same types (not necessarily in the depicted shape).\n" +
                "The tiles of one group can be different from those of another group.", "cg3.jpg");

        doublecheck= new int[6][5];
    }

    /**
     * Algorithm that search a single sequence of adjacent
     * cards with the same tile
     * @param matrix used to search the sequences
     * @return true if one is found
     */
    public boolean searchSeq(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols];

        int groupCount = 0;

        // Search for horizontal groups
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE) {
                    int value = matrix[i][j];
                    int groupSize = findGroup(matrix, visited, i, j, value, true, false);
                    if (groupSize >= 4) {
                        groupCount++;
                        copyMatrix(matrix);
                        if (groupCount >= 4) {
                            return true;
                        }
                    }
                }
            }
        }

        // Reset visited array
        visited = new boolean[rows][cols];
        count = 0;

        // Search for vertical groups
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE) {
                    int value = matrix[i][j];
                    int groupSize = findGroup(matrix, visited, i, j, value, false, false);
                    if (groupSize >= 4) {
                        groupCount++;
                        copyMatrix(matrix);
                        if (groupCount >= 4) {
                            return true;
                        }
                    }
                }
            }
        }

        // Reset visited array
        visited = new boolean[rows][cols];
        count =0;

        // Search for vertical groups
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (!visited[i][j] && matrix[i][j] != UNAVAILABLE) {
                    int value = matrix[i][j];
                    int groupSize = findGroup(matrix, visited, i, j, value, false, true);
                    if (groupSize >= 4) {
                        groupCount++;
                        copyMatrix(matrix);
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
      * @param row to check
      * @param col to check
      * @param value contained in tile
      * @return count of adjacent tiles group
      */
     private int findGroup(int[][] matrix, boolean[][] visited, int row, int col, int value, boolean horizontal, boolean shapes) {
         int rows = matrix.length;
         int cols = matrix[0].length;

         if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col] || matrix[row][col]==UNAVAILABLE || matrix[row][col] != value) {
             return 0;
         }

         visited[row][col] = true;
         matrix[row][col] = UNAVAILABLE;
         setChecked(row, col);

         count++;
         if(horizontal){
             count += findGroup(matrix, visited, row, col - 1, value,true, false); // Left
             count += findGroup(matrix, visited, row, col + 1, value, true, false); // Right
         }
         if(!horizontal && !shapes){
             count += findGroup(matrix, visited, row - 1, col, value, false, false); // Up
             count += findGroup(matrix, visited, row + 1, col, value, false, false); // Down
         }
         if(shapes){
             count += findGroup(matrix, visited, row, col - 1, value,false, true); // Left
             count += findGroup(matrix, visited, row, col + 1, value, false, true); // Right
             count += findGroup(matrix, visited, row - 1, col, value, false,true); // Up
             count += findGroup(matrix, visited, row + 1, col, value, false, true); // Down
         }
         if(count >= 4) return count;
         else return 0;
     }

     /**
      * Set an element of the doublecheck matrix as UNAVAILABLE
      * @param row to set UNAVAILABLE
      * @param col to set UNAVAILABLE
      */
     private void setChecked(int row, int col){
         doublecheck[row][col] = UNAVAILABLE;
     }

     /**
      * Copy doublecheck matrix into bookshelf generated matrix
      * @param m bookshelf's matrix
      */
     private void copyMatrix(int[][] m){
         for(int i = 0; i<6; i++){
             for (int j = 0; j<5; j++)if(doublecheck[i][j]==UNAVAILABLE) m[i][j]=UNAVAILABLE;
         }
     }


    @Override
    public int checkGoal(Bookshelf bs) {
        if(!isReached()){
            if(searchSeq(bs.getBookshelfColors())){
                goalReached();
                return getScore();
            }
        }
        return 0;
    }
}
