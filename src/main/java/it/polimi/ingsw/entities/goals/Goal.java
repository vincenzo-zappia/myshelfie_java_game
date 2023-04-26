package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

public interface Goal {
    int UNAVAILABLE = 104; //DEBUG use

    int checkGoal(Bookshelf bookshelf);
}
