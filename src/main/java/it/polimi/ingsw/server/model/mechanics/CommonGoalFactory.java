package it.polimi.ingsw.server.model.mechanics;

import it.polimi.ingsw.server.model.entities.goals.*;

public class CommonGoalFactory {

    public Goal makeCommonGoal(int goal_id){  //goal_id: intero che corrisponde alla cifra finale sul nome dei CommonGoalX
        if(goal_id==1)return new CommonGoal1();
        if(goal_id==2)return new CommonGoal2();
        if(goal_id==3)return new CommonGoal3();
        if(goal_id==4)return new CommonGoal4();
        if(goal_id==5)return new CommonGoal5();
        if(goal_id==6)return new CommonGoal6();
        if(goal_id==7)return new CommonGoal7();
        if(goal_id==8)return new CommonGoal8();
        if(goal_id==9)return new CommonGoal9();
        if(goal_id==10)return new CommonGoal10();
        if(goal_id==11)return new CommonGoal11();
        if(goal_id==12)return new CommonGoal12();
        return null;
    }
}
