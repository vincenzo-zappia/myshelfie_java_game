package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.goals.CommonGoal;
import it.polimi.ingsw.entities.goals.Goal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalFactoryTest {
    CommonGoalFactory commonGoalFactory;
    @BeforeEach
    void setUp() {
        commonGoalFactory = new CommonGoalFactory();
    }

    @Test
    void makeCommonGoal() {
       Goal[] array = commonGoalFactory.makeCommonGoal();
        assertNotEquals(array[0], array[1]);
    }
}