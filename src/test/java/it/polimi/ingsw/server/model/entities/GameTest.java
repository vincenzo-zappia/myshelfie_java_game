package it.polimi.ingsw.server.model.entities;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.mechanics.Game;
import it.polimi.ingsw.util.CardType;
import it.polimi.ingsw.util.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class GameTest {
    private static Game game;
    private static ArrayList<String> usernames;

    @BeforeAll
    static void setup(){
        usernames = new ArrayList<>();
        usernames.add("user1");
        usernames.add("user2");
        game = new Game(usernames);
    }

    @Test
    void insertCardInBookshelf(){
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card("book1.png", CardType.BOOKS));
        game.addCardToBookshelf("user1", 0, cards);
        Cell[][] bookshelf = game.getPlayerBookshelf("user1");
        assertFalse(bookshelf[5][0].isCellEmpty());
        assertTrue(cards.get(0).sameCard(bookshelf[5][0].getCard()));
    }

}
