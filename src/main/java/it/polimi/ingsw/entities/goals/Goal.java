package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

public interface Goal {
    int UNAVAILABLE = 104; //DEBUG use

    /**
     * checks if the player has completed a goal
     * @param bookshelf where method operates
     * @return points
     */
    int checkGoal(Bookshelf bookshelf);
}
