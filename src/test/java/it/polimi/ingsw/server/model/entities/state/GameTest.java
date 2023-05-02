package it.polimi.ingsw.server.model.entities.state;

import it.polimi.ingsw.entities.Player;
import it.polimi.ingsw.mechanics.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {
    Game game;

    /*
    @Test
    public void test_OderByScore() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("paolino");
        usernames.add("gemitaiz");
        usernames.add("celentano");
        usernames.add("mattarella");

        game = new Game(usernames);

        ArrayList<Player> list = game.orderByScore();
        assertEquals(4,list.size());
        assertEquals(5,list.get(0).getScore());
        assertEquals(15,list.get(1).getScore());
        assertEquals(21,list.get(2).getScore());
        assertEquals(3000,list.get(3).getScore());

        for(int i = 0; i < 4; i++)System.out.println(list.get(i).getScore());

    }

    @Test
    void testIsSelectable(){
        game = new Game(4);
        int[][] crd1 = {{0, 3}, {0, 4}};         //true
        int[][] crd2 = {{0, 0}, {1, 0}, {8, 6}}; //False
        int[][] crd3 = {{2, 1}, {2, 3}, {2, 7}}; //False
        int[][] crd4 = {{0, 3}, {1, 3}};         //true

        assertFalse(game.isSelectable(crd2));
        assertFalse(game.isSelectable(crd3));
        assertTrue(game.isSelectable(crd1));

        game = new Game(3);
        assertTrue(game.isSelectable(crd4));  //verifica per colonna



    }
*/
}
