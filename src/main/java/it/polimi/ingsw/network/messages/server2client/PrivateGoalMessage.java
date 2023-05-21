package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class PrivateGoalMessage extends Message {
    private final PrivateGoal privateGoal;

    public PrivateGoalMessage(PrivateGoal privateGoal) {
        super("server", MessageType.PRIVATE_GOAL);
        this.privateGoal = privateGoal;
    }

    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }

}
