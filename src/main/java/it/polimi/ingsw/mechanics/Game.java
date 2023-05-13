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
import it.polimi.ingsw.util.BoardCell;

import java.util.ArrayList;
import java.util.HashMap;
import it.polimi.ingsw.util.Cell;

public class Game{

    //region ATTRIBUTES
    private final Board board;
    private final HashMap<String, Player> players;
    private final Goal[] commonGoals;
    private final CommonGoal0 commonGoal0;
    //endregion

    //region CONSTRUCTOR
    public Game(ArrayList<String> usernames){
        board = new Board(usernames.size());
        board.fillBoard(); //filling of the board with cards
        players = new HashMap<>();

        //player (game entity) creation
        for(String user: usernames) players.put(user, new Player(user));

        CommonGoalFactory factory = new CommonGoalFactory();
        commonGoals = factory.makeCommonGoal();


        commonGoal0 = new CommonGoal0();
    }
    //endregion

    //region METHODS
    /**
     * Checks if the selected cards are actually selectable
     * @param coord coordinates of the selected cards
     * @return true if the cards are selectable, false otherwise
     */
    public boolean canSelect(int[][] coord){

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

    /**
     * Removes at most three cards from the board
     * @param coordinates of the cards selected to be removed
     * @return the removed cards
     */
    public ArrayList<Card> removeCardsFromBoard(int[][] coordinates){
        ArrayList<Card> removedCards = new ArrayList<>();
        for (int[] coordinate : coordinates) removedCards.add(board.removeCard(coordinate[0], coordinate[1]));
        return removedCards;
    }

    //TODO: Estrarre la validità dell'inserzione dal metodo di inserzione come per la selezione?
    /**
     * Inserts each selected card in order into the player's bookshelf
     * @param playerUsername player who makes the move
     * @param column into which the cards selected by the player are inserted
     * @param cards selected and arranged by the player in the desired order
     */
    public boolean addCardsToBookshelf(String playerUsername, int column, ArrayList<Card> cards) {
        //Checking if the selected row is an existing one
        if(column < 0 || column >= 5) return false;

        //Checking if the selected column has enough space for the number of cards selected
        if(!players.get(playerUsername).getBookshelf().getCell(cards.size() - 1, column).isCellEmpty()) return false; //TODO: -1 o no -1?

        //Card insertion
        for(Card c : cards) players.get(playerUsername).addCardToBookshelf(column, c);
        return true;
    }

    /**
     * Checks if the board has no more selectable cards
     * @return if every card doesn't have another adjacent card
     */
    public boolean checkRefill(){
        BoardCell[][] matrix = board.getMatrix();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!matrix[i][j].isCellActive()) continue;
                if((!matrix[i][j].isCellEmpty() && !matrix[i + 1][j].isCellEmpty())
                        || (!matrix[i][j].isCellEmpty() && !matrix[i][j + 1].isCellEmpty())) return false;
            }
        }
        board.fillBoard();
        return true;
    }

    /**
     * Checks if a common goal has been achieved by a player
     * @param username player whose eventual achievement we want to check
     */
    public void scoreCommonGoal(String username){
        Player p = players.get(username);
        System.out.println(commonGoals[0].getClass().toString());
        p.addScore(commonGoals[0].checkGoal(p.getBookshelf()));
        System.out.println(commonGoals[1].getClass().toString());
        p.addScore(commonGoals[1].checkGoal(p.getBookshelf()));
    }

    /**
     * For each player checks the progress of the achievement of his private goal and the general common goal 0
     */
    public void scorePrivateGoal(){
        for(Player p : players.values()){
            p.addScore(p.getPrivateGoal().checkGoal(p.getBookshelf()));
            p.addScore(commonGoal0.checkGoal(p.getBookshelf()));
        }
    }

    /**
     * make a scoreboard of usernames and scores of each user (NOT ORDERED)
     * @return scoreboard hashmap (username, score)
     */
    public HashMap<String, Integer> getScoreboard(){
        HashMap<String, Integer> scoreboard = new HashMap<>();
        for(String username: players.keySet())
            scoreboard.put(players.get(username).getUsername(), players.get(username).getScore());
        return scoreboard;
    }


    /**
     * Checks is the bookshelf of a given player is full (forwarding method)
     * @param username player whose bookshelf to check
     * @return if the bookshelf of the player is full
     */
    public boolean isPlayerBookshelfFull(String username){
        return players.get(username).isBookshelfFull();
    }
    //endregion

    //region GETTER AND SETTER
    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(players.values());
    }
    public Player getPlayer(String username){
        return players.get(username);
    }
    public Cell[][] getPlayerBookshelf(String username){
        return players.get(username).getBookshelf().getMatrix();
    }
    public Board getBoard(){
        return board;
    }
    public Goal[] getCommonGoals(){
        return commonGoals;
    }
    //endregion

}
