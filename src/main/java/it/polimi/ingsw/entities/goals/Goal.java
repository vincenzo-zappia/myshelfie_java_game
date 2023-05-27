package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;

/**
 * Basic functionality of any kind of goal
 */
public interface Goal {
    int UNAVAILABLE = 104; //DEBUG use

    /**
     * Checks if the player has achieved a goal
     * @param bookshelf of the player to check
     * @return points scored
     */
    int checkGoal(Bookshelf bookshelf);

}
