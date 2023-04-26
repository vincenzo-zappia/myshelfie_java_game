package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.goals.CommonGoal0;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommonGoal0Test {
    private static CommonGoal0 cg0;
    public static Bookshelf b;

    @BeforeAll
    public static void inizialize(){
        cg0 = new CommonGoal0();
        b = new Bookshelf();
    }
    @BeforeEach
    public void creationMatrix(){
        int[][] matrix = {
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,1,0,0,0},
                {1,1,0,1,0},
                {1,1,1,1,0}
        };

        cg0.setColorMatrix(matrix);
    }
    /*
    @Test
    public void test1(){
        int result = cg0.checkGoal(b);
        assertEquals(8, result);
    }

     */

}
