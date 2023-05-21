package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the details regarding the game common goals and each player's specific private goal
 */
public class CommonGoalsMessage extends Message {
    private final Goal[] commonGoals;

    //TODO: CommonGoal0?

    public CommonGoalsMessage(Goal[] commonGoals) {
        super("server", MessageType.COMMON_GOAL);
        this.commonGoals = commonGoals;
    }

    public Goal[] getCommonGoals() {
        return commonGoals;
    }

}
