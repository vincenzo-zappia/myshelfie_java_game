/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.1
 * Comments: none
 */

package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.Player;
import it.polimi.ingsw.entities.goals.CommonGoal0;
import it.polimi.ingsw.entities.goals.CommonGoal1;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.util.BoardCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import it.polimi.ingsw.entities.goals.*;
import it.polimi.ingsw.util.Cell;

public class Game{

    //region ATTRIBUTES
    private final Board board;
    private final HashMap<String, Player> players;
    private final ArrayList<Goal> commonGoals;
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
        //commonGoals = new Goal[2];
        //commonGoals = factory.makeCommonGoal(); //sets the common goals of the game
        commonGoals = new ArrayList<>();
        commonGoals.add(new CommonGoal10());


        commonGoal0 = new CommonGoal0();
    }
    //endregion

    //region METHODS

    public Cell[][] getPlayerBookshelf(String username){
        return players.get(username).getBookshelf().getMatrix();
    }

    /**
     * Checks if the selected cards are actually selectable
     * @param coord coordinates of the selected cards
     * @return true if the cards are selectable, false otherwise
     */
    public boolean isSelectable(int[][] coord){

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
    public ArrayList<Card> removeCardFromBoard(int[][] coordinates){
        ArrayList<Card> removedCards = new ArrayList<>();
        for (int[] coordinate : coordinates) removedCards.add(board.removeCard(coordinate[0], coordinate[1]));
        System.out.println("INFO: Cards removed!");
        return removedCards;
    }

    /**
     * Inserts each selected card in order into the player's bookshelf
     * @param playerUsername player who performs the move
     * @param column into which insert the cards selected by the player
     * @param cards selected and arranged by the player in the desired order
     * @throws AddCardException if column are already full
     */
    public boolean addCardToBookshelf(String playerUsername, int column, ArrayList<Card> cards) {
        //now the method check if the arraylist contains a player with username == playerUsername
        //TODO: controlli da spostare sul controller e rendere il metodo void
        if(column <0 || column>=5) return false;
        if(!players.get(playerUsername).getBookshelf().getCell(cards.size(), column).isCellEmpty()) return false;

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
        p.addScore(commonGoals.get(0).checkGoal(p.getBookshelf()));

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
     * Method that orders players by increasing point
     * @return the ordered list of players
     */
    public HashMap<String, Integer> orderByScore(){
        HashMap<String, Integer> ordered = new HashMap<>();
        for(String username : players.keySet()) ordered.put(username, players.get(username).getScore());

        //TODO: Effettivamente ordinarli
        return ordered;
    }

    //metodo forwarding
    public boolean isPlayerBookshelfFull(String username){
        return players.get(username).isBookshelfFull();
    }

    //endregion

    //region GETTER AND SETTER
    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(players.values());
    }
    public Board getBoard(){
        return board;
    }
    //endregion

}
