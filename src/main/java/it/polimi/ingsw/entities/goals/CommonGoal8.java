package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;

public class CommonGoal8 implements Goal {

    private static final int SCORE = 0; //TODO:inserire valore del goal
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        Card c1, c2, c3, c4;

        try {
            c1 = bookshelf.getCell(0,0).getCard();
            c2 = bookshelf.getCell(0,4).getCard();
            c3 = bookshelf.getCell(5,0).getCard();
            c4 = bookshelf.getCell(5,4).getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(c1.equals(c2) && c3.equals(c4) && c2.equals(c4)) return SCORE;
        else return 0;
    }
}
