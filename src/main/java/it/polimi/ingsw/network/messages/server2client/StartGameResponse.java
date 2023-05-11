package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.entities.goals.CommonGoal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class StartGameResponse extends Message {

    //region ATTRIBUTES
    private final boolean response;
    private final CommonGoal[] commonGoals;
    private final PrivateGoal privateGoal;
    //endregion

    //region CONSTRUCTOR
    public StartGameResponse(boolean response, CommonGoal[] commonGoals, PrivateGoal privateGoal, String content) {
        super("server", MessageType.START_GAME_RESPONSE);
        this.commonGoals = commonGoals;
        this.privateGoal = privateGoal;
        this.response = response;
        setContent(content);
    }
    //endregion

    //region GETTER
    public CommonGoal[] getCommonGoals() {
        return commonGoals;
    }

    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }

    public boolean getResponse() {
        return response;
    }
    //endregion

}
