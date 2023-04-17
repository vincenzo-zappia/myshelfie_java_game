/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.server.model.state;

import it.polimi.ingsw.server.model.entities.Board;
import it.polimi.ingsw.server.model.entities.Card;
import it.polimi.ingsw.server.model.entities.Player;
import it.polimi.ingsw.server.model.entities.goals.Goal;
import it.polimi.ingsw.exceptions.AddCardException;

import java.util.ArrayList;
import java.util.Collections;

public class Game{

    //region ATTRIBUTES
    private final Board board;
    //private ArrayList<Bookshelf> bookshelves; //Game is the class that puts together multiple entities and has specific value
    private ArrayList<Player> players;
    private int playerNum;
    private Goal commonGoal1;
    private Goal commonGoal2;
    private boolean endGame;
    //endregion

    //region CONSTRUCTOR
    public Game(int playerNum){
        board = new Board(playerNum);
        board.fillBoard(); //filling of the board with cards
        players=new ArrayList<>();
        /*
        //initialization of playerNum bookshelves
        bookshelves = new Bookshelf[playerNum];
        for (Bookshelf b : bookshelves){
            b = new Bookshelf();
        }
        */
        System.out.println(board);
    }
    //endregion

    //region METHODS

    //TODO: in questo modo il giocatore deve inviare la selezione e aspettare che venga valutata, bisognerebbe implementare con feedback in tempo reale
    public boolean isSelectable(int[][] coord){

        //"coord" is a matrix with the coordinates of the max 3 cards that the player selected:
        //pos = {
        //  {x1, y1},
        //  {x2, y2},
        //  {x3, y3}
        //}

        if(coord.length==3 && (coord[0][0] == coord[1][0]+1 && coord[1][0] == coord[2][0]+1 || coord[0][1] == coord[1][1]+1 && coord[1][1] == coord[2][1]+1) )
        {
            int cntr = 0;
            for(int i = 0; i < 3; i++)if(board.selectableCard(coord[i][0], coord[i][1]))cntr++;
            if(cntr==3)return true;
        }
        if(coord.length==2 && (coord[0][0] == coord[1][0]+1 || coord[0][1] == coord[1][1]+1) )
        {
            int cntr = 0;
            for(int i = 0; i < 2; i++)if(board.selectableCard(coord[i][0], coord[i][1]))cntr++;
            if(cntr==2)return true;
        }
        if(coord.length==3 && (coord[0][0] == coord[1][0]-1 && coord[1][0] == coord[2][0]-1 || coord[0][1] == coord[1][1]-1 && coord[1][1] == coord[2][1]-1) )
        {
            int cntr = 0;
            for(int i = 0; i < 3; i++)if(board.selectableCard(coord[i][0], coord[i][1]))cntr++;
            if(cntr==3)return true;
        }
        if(coord.length==2 && (coord[0][0] == coord[1][0]-1 || coord[0][1] == coord[1][1]-1) )
        {
            int cntr = 0;
            for(int i = 0; i < 2; i++)if(board.selectableCard(coord[i][0], coord[i][1]))cntr++;
            if(cntr==2)return true;
        }
        if(coord.length==1)return board.selectableCard(coord[0][0], coord[0][1]);
        return false;
    }

    //method that calls "Board.removeCard()" checking if it's the player's turn
    //"coordinates" is a matrix with the coordinates of the max 3 cards that the player selected:
    //pos = {
    //  {x1, y1},
    //  {x2, y2},
    //  {x3, y3}
    //}
    public void removeCardFromBoard(int[][] coordinates){
        for(int i = 0; i < coordinates.length; i++) board.removeCard(coordinates[i][0], coordinates[i][1]);
        System.out.println("Cards removed!");
    }

    public void addCardToBookshelf(String playerUsername, int column, Card[] cards) throws AddCardException {
        int playerIndex = players.indexOf(playerUsername); //TODO: exception in case username not found

        //inserts each selected card into the player's bookshelf
        for(Card c : cards){
            players.get(playerIndex).getBookshelf().addCard(column, c);
        }
    }

    public void scoreCommonGoal(){
        for(Player p : players){
            p.addScore(commonGoal1.checkGoal(p.getBookshelf()));
            p.addScore(commonGoal2.checkGoal(p.getBookshelf()));
        }
    }

    //method that checks each player's private goal (can be called during and at the end of the game
    //TODO: deciding when the method will be called (end game or repeatedly mid game)
    public void scorePrivateGoal(){
        for(Player p : players){
            p.addScore(p.getPrivateGoal().checkGoal(p.getBookshelf()));
        }
    }

    public ArrayList<Player> orderByScore(){
        ArrayList<Player> ordered = players;
        Collections.sort(ordered);
        return ordered;
    }

    public boolean isPlayerBookshelfFull(String username){
        boolean full = false;
        for(Player p : players){
            if(p.getUsername().equals(username)) full = p.isBookshelfFull();
        }
        return full;
    }

    public void addPlayers (Player x){    //used for test purpose
        players.add(x);
    }

    //endregion

    //region GETTER AND SETTER
    public ArrayList<Player> getPlayers(){
        return players;
    }

    //endregion

}
