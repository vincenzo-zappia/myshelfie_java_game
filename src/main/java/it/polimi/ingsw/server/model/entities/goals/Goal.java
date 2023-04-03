package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

public interface Goal {
    static final int UNAVAILABLE = 104; //DEBUG use
    static final int FLAGGED = 99;

    public int checkGoal(Bookshelf bookshelf);
}
