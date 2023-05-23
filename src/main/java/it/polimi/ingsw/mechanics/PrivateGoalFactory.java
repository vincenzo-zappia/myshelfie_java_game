package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.goals.PrivateGoal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PrivateGoalFactory {
    private final ArrayList<Integer> privateGoals  = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11));


    public PrivateGoal makePrivateGoal(){
        Random random = new Random();
        int index = random.nextInt(privateGoals.size());
        int id = privateGoals.get(index);
        privateGoals.remove(index);

        return ToolXML.getPrivateGoal(id);

    }

}
