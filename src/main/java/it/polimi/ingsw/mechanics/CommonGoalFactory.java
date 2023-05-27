package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.goals.*;

import java.util.Random;

public class CommonGoalFactory {

    /**
     * Returns an instance of a CommonGoal using an integer parameter
     * @param goal_id integer value that decides which CommonGoal to return
     * @return the chosen CommonGoal
     */
    private Goal goalFactory(int goal_id){
        if(goal_id==1) return new CommonGoal1();
        if(goal_id==2) return new CommonGoal2();
        if(goal_id==3) return new CommonGoal3();
        if(goal_id==4) return new CommonGoal4();
        if(goal_id==5) return new CommonGoal5();
        if(goal_id==6) return new CommonGoal6();
        if(goal_id==7) return new CommonGoal7();
        if(goal_id==8) return new CommonGoal8();
        if(goal_id==9) return new CommonGoal9();
        if(goal_id==10) return new CommonGoal10();
        if(goal_id==11) return new CommonGoal11();
        if(goal_id==12) return new CommonGoal12();
        return null;
    }

    /**
     * Randomly picks the two common goals of the game
     * @return array of two CommonGoal
     */
    public Goal[] makeCommonGoal(){  //goal_id: intero che corrisponde alla cifra finale sul nome dei CommonGoalX
        Random x = new Random();
        int tmp;
        int sentinel = 0;
        Goal[] result = new Goal[2];

        int i =0;
        do{
            tmp = x.nextInt(12)+1 ;
            if (sentinel != tmp){
                result[i]=goalFactory(tmp);
                sentinel=tmp;
                if(i==1)i=0;
                i++;
            }
        }while(result[0] == null || result[1] == null);
        return result;
    }

}
