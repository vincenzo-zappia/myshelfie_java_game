package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/*
 * Three columns each formed by 6 tiles of maximum three different types.
 * One column can show the same or a different combination of another column.
 */

public class CommonGoal5 extends CommonGoal implements Goal{

    public CommonGoal5() {
        super("Three columns each formed by 6 tiles of maximum three different types.\n" +
                "One column can show the same or a different combination of another column.", "cg5.jpg");
    }

    @Override
    public int checkGoal(Bookshelf bs) {

        if (findColumns(bs.getBookshelfColors())) return getScore();
        return 0;
    }

    public boolean findColumns(int[][] m) {
        int counter = 0;

        for (int j = 0; j < m[0].length; j++) {
            int[] diff = new int[m.length];  //array of column
            int tmp = 0;

            for (int[] ints : m) {
                int n = ints[j];
                if (!isInColumn(diff, n)) {
                    diff[tmp] = n;
                    tmp++;

                    if (tmp > 3) break;
                }
            }
            if (tmp <= 3) {
                counter++;
                if (counter >= 3) return true;
            }
        }
        return false;
    }


    private boolean isInColumn(int[] v, int n) {
        for (int j : v) if (j == n && j != UNAVAILABLE) return true;
        return false;
    }
}
