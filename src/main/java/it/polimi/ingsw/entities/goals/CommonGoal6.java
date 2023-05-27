package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/**
 * Two lines each formed by 5 different types of tiles.
 * One line can show the same or a different combination of the other line.
 */
public class CommonGoal6 extends CommonGoal implements Goal{

    public CommonGoal6() {
        super("Two lines each formed by 5 different types of tiles.\n" +
                "One line can show the same or a different combination of the other line.", "cg6.jpg");
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {

        int count=0;
        for(int i=0; i<6; i++){
            if (!(bookshelf.cardsInRow(i)==5)) continue;
            if (allDifferentType(bookshelf.getRow(i))) count++;
        }

        if(count>=2) return getScore();
        else return 0;
    }

}
