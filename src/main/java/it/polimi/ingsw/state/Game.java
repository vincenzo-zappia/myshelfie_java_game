/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package proj.polimi.testrj45.state;

import proj.polimi.testrj45.entities.Board;
import proj.polimi.testrj45.entities.Card;
import proj.polimi.testrj45.entities.Player;
import proj.polimi.testrj45.entities.goals.Goal;

public class Game{

    //REGION ATTRIBUTES
    private Board board;
    //private Bookshelf[] bookshelves;
    private Player[] players;
    private int playerNum;
    private GameState state;
    private int currentPlayer; //current turn player's index in "players"
    private Goal commonGoal1;
    private Goal commonGoal2;
    private boolean endGame;

    //END REGION

    //REGION CONSTRUCTOR
    public Game(int playerNum){
        board = new Board(playerNum);
        board.fillBoard(); //filling of the board with cards

        /*
        //initialization of playerNum bookshelves
        bookshelves = new Bookshelf[playerNum];
        for (Bookshelf b : bookshelves){
            b = new Bookshelf();
        }
        */
        System.out.println(board);
    }
    //END REGION

    //REGION METHODS

    //method that calls "Board.removeCard()" checking if it's the player's turn
    //"coordinates" is a matrix with the coordinates of the max 3 cards that the player selected:
    //pos = {
    //  {x1, y1},
    //  {x2, y2},
    //  {x3, y3}
    //}
    public void removeCardPlayer(int[][] coordinates){
        //TODO: exception when illegal move (it will actually be implemented in GameController)
        for(int i = 0; i < coordinates.length; i++) board.removeCard(coordinates[i][0], coordinates[i][1]);
        System.out.println("Cards removed. Ready for the bookshelf!");
    }

    public String addCardToBookshelf(Player sender, int column, Card[] cards){
        if (!players[currentPlayer].equals(sender)){
            //TODO: exit code with error
            return "Player not allowed!";
        }
        //TODO: iterator that cycles from 0 to (playerNum - 1) indefinitely

        return "tutto ok";
    }

    public void scoreCommonGoal(){
        for(Player p : players){
            p.addScore(commonGoal1.checkGoal(p.getBookshelf()));
            p.addScore(commonGoal2.checkGoal(p.getBookshelf()));
        }
    }
//    public String toString(){
//        return board.toString();
//    }
}
