package it.polimi.ingsw.server.model.entities.state;

import it.polimi.ingsw.server.model.entities.Player;
import it.polimi.ingsw.server.model.state.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {
    Game game;

    @Test
    public void test_OderByScore() {
        game = new Game(4);
        Player p1 = new Player("paolino",1);
        Player p2 = new Player("gemitaiz",1);
        Player p3 = new Player("celentano",1);
        Player p4 = new Player("mattarella",1);

        p1.setScore(3000);
        p2.setScore(15);
        p3.setScore(21);
        p4.setScore(5);

        game.addPlayers(p1);
        game.addPlayers(p2);
        game.addPlayers(p3);
        game.addPlayers(p4);

        ArrayList<Player> list = game.orderByScore();
        assertEquals(4,list.size());
        assertEquals(5,list.get(0).getScore());
        assertEquals(15,list.get(1).getScore());
        assertEquals(21,list.get(2).getScore());
        assertEquals(3000,list.get(3).getScore());

        for(int i = 0; i < 4; i++)System.out.println(list.get(i).getScore());

    }

}
