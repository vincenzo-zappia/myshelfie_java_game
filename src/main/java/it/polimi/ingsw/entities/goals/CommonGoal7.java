package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/*
 * Four lines each formed by 5 tiles of maximum three different types.
 * One line can show the same or a different combination of another line.
 */

public class CommonGoal7 extends CommonGoal implements Goal{

    public CommonGoal7() {
        super("Four lines each formed by 5 tiles of maximum three different types.\n" +
                "One line can show the same or a different combination of another line.", "cg7.png");
    }

    @Override
    public int checkGoal(Bookshelf bs) {
        int tmp;
        int cntr=0;

        for (int i = 0; i < 6; i++){
            tmp = 0;
            if(!existEmpty(bs.getRow(i))){
                for(int j = 0; j< bs.getRow(i).length-1; j++){
                    if(!bs.getRow(i)[j].isTileEmpty()){
                        if(!bs.getRow(i)[j].getCard().sameType(bs.getRow(i)[j+1].getCard()))tmp++;
                    }
                }
                if(tmp<4)cntr++;
            }
        }
        if(cntr>=4) return getScore();
        return 0;
    }
}
