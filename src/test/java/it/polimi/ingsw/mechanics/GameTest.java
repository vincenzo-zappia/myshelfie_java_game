package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.Scoreboard;
import it.polimi.ingsw.entities.goals.CommonGoal1;
import it.polimi.ingsw.entities.goals.CommonGoal5;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.view.cli.CliUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setup(){
        ArrayList<String> usernameList = new ArrayList<>(Arrays.asList("Qui", "Quo", "Qua", "Paperino"));
        game = new Game(usernameList);
    }

    @Test
    void canSelect() {
        assertFalse(game.getPlayer("Qui").getBookshelf().isBookshelfFull());

        //Wrong diagonal
        int[][] selection = new int[][]{{0, 4}, {1, 5}, {2, 6}};
        assertFalse(game.canSelect("Qui", selection));

        //Correct single
        selection = new int[][]{{1, 3}};
        assertTrue(game.canSelect("Qui", selection));

        //Wrong single
        selection = new int[][]{{3, 3}};
        assertFalse(game.canSelect("Qui", selection));

        //Correct column double
        selection = new int[][]{{3, 8}, {4, 8}};
        assertTrue(game.canSelect("Qui", selection));

        //Correct row double
        selection = new int[][]{{0, 3}, {0, 4}};
        assertTrue(game.canSelect("Qui", selection));

        //Allowing for a triple selection
        selection = new int[][]{{0, 3}, {0, 4}};
        game.removeSelectedCards(selection);

        //Correct row triple
        selection = new int[][]{{1, 3}, {1, 4}, {1, 5}};
        assertTrue(game.canSelect("Qui", selection));

        //Wrong row triple
        selection = new int[][]{{4, 5}, {4, 6}, {4, 7}};
        assertFalse(game.canSelect("Qui", selection));
    }

    @Test
    void cannotSelectBookshelfAlmostFull(){
        assertFalse(game.getPlayer("Qui").getBookshelf().isBookshelfFull());

        //Creating the 6 cards selection to insert
        ArrayList<Card> insertedCards = new ArrayList<>();
        Card card = new Card("frames1.png", CardType.FRAMES);
        for(int i = 0; i < 6; i++) insertedCards.add(card);
        assertEquals(6, insertedCards.size());

        //Filling up the first 4 column of the player's bookshelf
        for(int i = 0; i < 4; i++) game.addCardsToBookshelf("Qui", i, insertedCards);
        int cardsInColumn;
        for(int i = 0; i < 4; i++) {
            cardsInColumn = game.getPlayer("Qui").getBookshelf().cardsInColumn(i);
            assertEquals(6, cardsInColumn);
        }

        //Creating a 3 cards selection to insert and adding it to the last column
        insertedCards.clear();
        for(int i = 0; i < 3; i++) insertedCards.add(card);
        assertEquals(3, insertedCards.size());
        game.addCardsToBookshelf("Qui", 4, insertedCards);

        //Allowing for a 3 card selection
        int[][] selection = new int[][]{{0, 3}, {0, 4}};
        game.removeSelectedCards(selection);

        //Verifying that with 3 slots left the player can select up to 3 cards
        selection = new int[][]{{1, 3}, {1, 4}, {1, 5}};
        assertTrue(game.canSelect("Qui", selection));

        //Verifying that with 2 slots left the player cannot select 3 cards
        insertedCards.clear();
        insertedCards.add(card);
        game.addCardsToBookshelf("Qui", 4, insertedCards);
        assertFalse(game.canSelect("Qui", selection));

        //Verifying that with 2 slots left the player can select 2 cards
        selection = new int[][]{{1, 3}, {1, 4}};
        assertTrue(game.canSelect("Qui", selection));

        //Verifying that with 1 slot left the player cannot select 2 cards
        game.addCardsToBookshelf("Qui", 4, insertedCards);
        assertFalse(game.canSelect("Qui", selection));

        //Verifying that with 1 slot left the player can select 1 cards
        selection = new int[][]{{1, 3}};
        assertTrue(game.canSelect("Qui", selection));

    }

    @Test
    void removeSelectedCards() {
        int[][] selectedCards = new int[][]{{0, 3}, {0, 4}};
        ArrayList<Card> removedCards = new ArrayList<>();
        removedCards.add(game.getBoard().getCard(0, 3));
        removedCards.add(game.getBoard().getCard(0, 4));
        assertEquals(removedCards, game.removeSelectedCards(selectedCards));
    }

    @Test
    void canInsert(){

        //Verifying that no insertion can be made out of column boundaries
        assertFalse(game.canInsert("Qui", 20, 2));
        assertFalse(game.canInsert("Qui", -1, 2));

        //Verifying that an empty column can be filled with up to 3 cards
        int cardsInColumn = game.getPlayer("Qui").getBookshelf().cardsInColumn(0);
        assertEquals(0, cardsInColumn);
        assertTrue(game.canInsert("Qui", 0, 1));
        assertTrue(game.canInsert("Qui", 0, 2));
        assertTrue(game.canInsert("Qui", 0, 3));

        //Verifying that no less than 1 card and no more than 3 cards can be inserted into the column
        assertFalse(game.canInsert("Qui", 0, 0));
        assertFalse(game.canInsert("Qui", 0, 4));

        //Creating a 6 cards selection to insert
        ArrayList<Card> insertedCards = new ArrayList<>();
        Card card = new Card("frames1.png", CardType.FRAMES);
        for(int i = 0; i < 6; i++) insertedCards.add(card);
        assertEquals(6, insertedCards.size());

        //Adding the cards to the bookshelf
        game.addCardsToBookshelf("Qui", 0, insertedCards);

        //Verifying that no more cards can be added into the now empty column
        cardsInColumn = game.getPlayer("Qui").getBookshelf().cardsInColumn(0);
        assertEquals(6, cardsInColumn);
        assertFalse(game.canInsert("Qui", 0, 1));
        assertFalse(game.canInsert("Qui", 0, 2));
        assertFalse(game.canInsert("Qui", 0, 3));

    }

    @Test
    void addCardsToBookshelf() {

        //Verifying that the newly initialized bookshelf has an empty first column
        int cardsInColumn = game.getPlayer("Qui").getBookshelf().cardsInColumn(0);
        assertEquals(0, cardsInColumn);

        //Creating a 3 cards selection to insert
        ArrayList<Card> insertedCards = new ArrayList<>();
        Card card = new Card("frames1.png", CardType.FRAMES);
        for(int i = 0; i < 3; i++) insertedCards.add(card);
        assertEquals(3, insertedCards.size());

        //Adding 3 cards in column 0
        game.addCardsToBookshelf("Qui", 0, insertedCards);

        //Verifying that 3 cards were added into the column
        cardsInColumn = game.getPlayer("Qui").getBookshelf().cardsInColumn(0);
        assertEquals(3, cardsInColumn);

    }

    @Test
    void isPlayerBookshelfFull() {
        assertFalse(game.getPlayer("Qui").getBookshelf().isBookshelfFull());

        //Creating a 6 cards selection to insert
        ArrayList<Card> insertedCards = new ArrayList<>();
        Card card = new Card("frames1.png", CardType.FRAMES);
        for(int i = 0; i < 6; i++) insertedCards.add(card);
        assertEquals(6, insertedCards.size());

        //Filling up the player's bookshelf
        for(int i = 0; i < 5; i++) game.addCardsToBookshelf("Qui", i, insertedCards);
        int cardsInColumn;
        for(int i = 0; i < 5; i++) {
            cardsInColumn = game.getPlayer("Qui").getBookshelf().cardsInColumn(i);
            assertEquals(6, cardsInColumn);
        }

        assertTrue(game.isPlayerBookshelfFull("Qui"));
    }

    @Test
    void checkRefill(){

        //Verifying that a newly created board doesn't need to be refilled
        assertFalse(game.checkRefill());

        //Verifying that the board doesn't need to be refilled after just one selection
        int[][] selection = new int[1][2];
        selection[0][0] = 0;
        selection[0][1] = 3;
        game.removeSelectedCards(selection);
        assertFalse(game.checkRefill());

        //Verifying that an empty board needs to be refilled
        emptyBoard();
        assertTrue(game.checkRefill());

        //Adding a random card and verifying that with only one card the board needs to be refilled
        emptyBoard();
        BoardTile[][] matrix = game.getBoard().getBoard();
        matrix[4][5].setCard(new Card("frames1.png", CardType.FRAMES));
        assertTrue(game.getBoard().selectableCard(4, 5));
        assertTrue(game.checkRefill());

        //Creating a 2 card row and verifying that the board doesn't need to be refilled
        emptyBoard();
        matrix[4][5].setCard(new Card("frames1.png", CardType.FRAMES));
        assertTrue(game.getBoard().selectableCard(4, 5));
        matrix[4][6].setCard(new Card("frames1.png", CardType.FRAMES));
        assertTrue(game.getBoard().selectableCard(4, 6));
        assertFalse(game.checkRefill());

    }

    /**
     * Checks if the treeMap that represent scoreboard is effectively
     * order by descendent score
     */
    @Test
    void orderByScore(){
        game.getPlayer("Qui").addScore(10);
        game.getPlayer("Quo").addScore(99);
        game.getPlayer("Qua").addScore(9);
        game.getPlayer("Paperino").addScore(12);

        Scoreboard res = game.orderByScore();

        for (int i = 0; i < 4; i++) System.out.println(res.getScores(i));

        assertTrue(res.getScores(0)>res.getScores(1));
        assertTrue(res.getScores(1)>res.getScores(2));
        assertTrue(res.getScores(2)>res.getScores(3));
    }

    /**
     * Checks that casual combination of cards in bookshelf are not recognized by checkGoal()
     * and that score isn't effectively added to player's attribute
     */
    @Test
    void scoreCommonGoalCaseFalse(){
        for(int i=0; i<2; i++) {
            game.getPlayer("Qui").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("Qui").getBookshelf().addCard(i, new Card("img.png", CardType.GAMES));
            game.getPlayer("Qui").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("Qui").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] tmp = new Goal[2];
        tmp[0] = new CommonGoal1();
        tmp[1] = new CommonGoal1();

        game.setCommonGoals(tmp);
        game.scoreCommonGoal("Qui");
        assertFalse(game.getPlayer("Qui").getScore()>0);

        for(int i=0; i<3; i++) {
            game.getPlayer("Quo").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
            game.getPlayer("Quo").getBookshelf().addCard(i, new Card("img.png", CardType.CATS));
            game.getPlayer("Quo").getBookshelf().addCard(i, new Card("img.png", CardType.BOOKS));
            game.getPlayer("Quo").getBookshelf().addCard(i, new Card("img.png", CardType.GAMES));
            game.getPlayer("Quo").getBookshelf().addCard(i, new Card("img.png", CardType.PLANTS));
            game.getPlayer("Quo").getBookshelf().addCard(i, new Card("img.png", CardType.FRAMES));
        }
        Goal[] v = new Goal[2];
        v[0] = new CommonGoal5();
        v[1] = new CommonGoal5();
        game.setCommonGoals(v);
        game.scoreCommonGoal("Quo");
        assertFalse(game.getPlayer("Quo").getScore()>0);
    }

    /**
     * Checks that CommonGoals (1 and 5) are truly recognized by checkGoal()
     * and that score is effectively added to player's attribute
     */
    @Test
    void scoreCommonGoalCaseTrue() {
        ArrayList<Card> cards = new ArrayList<>();
        Goal[] tmp = new Goal[2];
        tmp[0] = new CommonGoal1();
        tmp[1] = new CommonGoal1();
        game.setCommonGoals(tmp);

        for(int i=0; i<2; i++) {
           cards.add(new Card("img.png", CardType.FRAMES));
        }
        game.addCardsToBookshelf("Qui", 0, cards);
        game.addCardsToBookshelf("Qui", 1, cards);

        for(int i=0; i<2; i++) {
            cards.add(new Card("img.png", CardType.BOOKS));
        }
        game.addCardsToBookshelf("Qui", 3, cards);
        game.addCardsToBookshelf("Qui", 4, cards);


        game.scoreCommonGoal("Qui");
        System.out.println(game.getPlayer("Qui").getScore());
        assertTrue(game.getPlayer("Qui").getScore()>0);
    }

    /**
     * check if private goals scoring works
     */
    @Test
    void scorePrivateGoal() {
        game.getPlayer("Quo").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(0, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(0, new Card("img.png", CardType.PLANTS));

        game.getPlayer("Quo").getBookshelf().addCard(2, new Card("img.png", CardType.TROPHIES));
        game.getPlayer("Quo").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(2, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(2, new Card("img.png", CardType.PLANTS));

        game.getPlayer("Quo").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(3, new Card("img.png", CardType.BOOKS));
        game.getPlayer("Quo").getBookshelf().addCard(3, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(3, new Card("img.png", CardType.PLANTS));

        game.getPlayer("Quo").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(4, new Card("img.png", CardType.FRAMES));
        game.getPlayer("Quo").getBookshelf().addCard(4, new Card("img.png", CardType.CATS));
        game.getPlayer("Quo").getBookshelf().addCard(4, new Card("img.png", CardType.PLANTS));

        game.scorePrivateGoal();
        assertTrue(game.getPlayer("Quo").getScore()>0);
    }

    private void emptyBoard(){
        int[][] selection = new int[1][2];

        //Emptying the board
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(game.getBoard().selectableCard(i, j)){
                    selection[0][0] = i;
                    selection[0][1] = j;
                    game.removeSelectedCards(selection);
                }
            }
        }
    }

}