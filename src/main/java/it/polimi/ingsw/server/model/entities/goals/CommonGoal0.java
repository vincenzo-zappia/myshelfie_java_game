package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

/*
 * Questo CommonGoal si riferisce ai Goals presente sulla Board
 */

public class CommonGoal0 implements Goal{
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        return 0;
    }
}
