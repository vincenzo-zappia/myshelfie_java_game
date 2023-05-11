package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class GoalsMessage extends Message {
    private final Goal[] commonGoals;
    private final PrivateGoal privateGoal;

    public GoalsMessage(Goal[] commonGoals, PrivateGoal privateGoal) {
        super("server", MessageType.GOALS_DETAILS);
        this.commonGoals = commonGoals;
        this.privateGoal = privateGoal;
    }

    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }

    public Goal[] getCommonGoals() {
        return commonGoals;
    }
}
