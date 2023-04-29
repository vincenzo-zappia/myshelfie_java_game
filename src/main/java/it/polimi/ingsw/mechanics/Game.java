/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.Player;
import it.polimi.ingsw.entities.goals.CommonGoal0;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.exceptions.AddCardException;

import java.util.ArrayList;
import java.util.Collections;

public class Game{

    //region ATTRIBUTES
    private final Board board;
    private ArrayList<Player> players;
    private final Goal[] commonGoals;
    private CommonGoal0 commonGoal0;
    //endregion

    //region CONSTRUCTOR
    public Game(ArrayList<String> usernames){
        board = new Board(usernames.size());
        board.fillBoard(); //filling of the board with cards
        players=new ArrayList<>();

        //player (game entity) creation
        for(String user: usernames) players.add(new Player(user));

        commonGoals = new CommonGoalFactory().makeCommonGoal(); //sets the common goals of the game
        commonGoal0 = new CommonGoal0();
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
    public ArrayList<Card> removeCardFromBoard(int[][] coordinates){
        ArrayList<Card> removedCards = new ArrayList<>();
        for (int[] coordinate : coordinates) removedCards.add(board.removeCard(coordinate[0], coordinate[1]));
        System.out.println("Cards removed!");
        return removedCards;
    }

    public void addCardToBookshelf(String playerUsername, int column, ArrayList<Card> cards) throws AddCardException {
        int playerIndex = players.indexOf(playerUsername); //TODO: exception in case username not found

        //inserts each selected card into the player's bookshelf
        for(Card c : cards){
            players.get(playerIndex).getBookshelf().addCard(column, c);
        }
    }

    //TODO: da revisionare
    public void scoreCommonGoal(String username){
        for(Player p : players){
            if(p.getUsername().equals(username)) {
                p.addScore(commonGoals[0].checkGoal(p.getBookshelf()));
                p.addScore(commonGoals[1].checkGoal(p.getBookshelf()));
            }
        }
    }


    public void scorePrivateGoal(){
        for(Player p : players){
            p.addScore(p.getPrivateGoal().checkGoal(p.getBookshelf()));
            p.addScore(commonGoal0.checkGoal(p.getBookshelf()));
        }
    }

    public ArrayList<Player> orderByScore(){
        ArrayList<Player> ordered = players;
        Collections.sort(ordered);
        return ordered;
    }

    //metodo forwarding
    public boolean isPlayerBookshelfFull(String username){
        boolean full = false;
        for(Player p : players){
            if(p.getUsername().equals(username)) full = p.isBookshelfFull();
        }
        return full;
    }

    //endregion

    //region GETTER AND SETTER
    public ArrayList<Player> getPlayers(){
        return players;
    }

    //endregion

}
