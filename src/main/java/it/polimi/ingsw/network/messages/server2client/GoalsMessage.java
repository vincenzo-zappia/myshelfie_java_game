package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message containing the details regarding the game common goals and each player's specific private goal
 */
public class GoalsMessage extends Message {
    private final Goal[] commonGoals;
    private final PrivateGoal privateGoal;

    public GoalsMessage(Goal[] commonGoals, PrivateGoal privateGoal) {
        super("server", MessageType.GOALS_DETAILS);
        this.commonGoals = commonGoals;
        this.privateGoal = privateGoal;
    }

    public Goal[] getCommonGoals() {
        return commonGoals;
    }
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }

}
