package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;


/**
 * Two columns each formed by 6 different type of tiles
 */
public class CommonGoal2 extends CommonGoal implements Goal {

    public CommonGoal2() {
        super("Two columns each formed by 6 different type of tiles.", "cg2.jpg");
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {

        int count=0;
        for(int i=0; i<5; i++){
            if (!(bookshelf.cardsInColumn(i) == 6)) continue;
            if (allDifferentType(bookshelf.getColumn(i))) count++;
        }

        if (count>=2) return getScore();
        else return 0;
    }

}
