package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

public interface Goal {
    int UNAVAILABLE = 104; //DEBUG use

    int checkGoal(Bookshelf bookshelf);
}
