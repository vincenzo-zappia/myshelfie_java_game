package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;

public interface Goal {
    //TODO: attributes in and interface?
    int UNAVAILABLE = 104; //DEBUG use

    int checkGoal(Bookshelf bookshelf);
}
