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
import it.polimi.ingsw.entities.goals.*;
import it.polimi.ingsw.util.BoardCell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

import it.polimi.ingsw.util.Cell;

public class Game{

    //region ATTRIBUTES
    private final Board board;
    private final HashMap<String, Player> players;
    private Goal[] commonGoals;
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

    //TODO: Per ogni errore specificare il tipo attraverso il valore di un'enumerazinoe di errori
    //region METHODS
    /**
     * Checks if the selected cards are actually selectable
     * @param coord coordinates of the selected cards:
     *              coord[x][0] = row
     *              coord[x][1] = column
     * @return true if the cards are selectable, false otherwise
     */
    public boolean canSelect(String playerUsername, int[][] coord){

        //Checking if the player has selected more cards than he can insert into his bookshelf
        Bookshelf bookshelf = players.get(playerUsername).getBookshelf();

        boolean sentinel = false;
        for(int i = 0; i < 5; i++) if(6 - bookshelf.cardsInColumn(i) >= coord.length){
            sentinel = true;
            break;
        }
        if (!sentinel) return false;

        //Checking if any of the coordinates exceeds the board dimensions
        for (int[] i : coord) for (int j : i) if (j < 0 || j > 8) return false;

        //Ordering the selected cards to allow for a discontinuous selection
        if (coord.length > 1) {
            int[][] tmp = coord;
            if (isDiagonal(coord)) return false;
            coord = coordOrder(tmp);
        }

        //Checking if the selection of 3 cards is either in a row or a column
        if (coord.length == 3 && (coord[0][0] == coord[1][0] + 1 && coord[1][0] == coord[2][0] + 1  //card1.x = card2.x-1 = card3.x-2
                || coord[0][1] == coord[1][1] + 1 && coord[1][1] == coord[2][1] + 1))            //card1.x = card2.x = card3.x
        {
            int cntr = 0;
            for (int i = 0; i < 3; i++) if (board.selectableCard(coord[i][0], coord[i][1])) cntr++;
            if (cntr == 3) return true;
        }

        //Checking if the selection of 2 cards is either in a row or a column
        if (coord.length == 2 && (coord[0][0] == coord[1][0] + 1         //card1.x = card2.x-1
                || coord[0][1] == coord[1][1] + 1))                   //card1.x = card2.x
        {
            int cntr = 0;
            for (int i = 0; i < 2; i++) if (board.selectableCard(coord[i][0], coord[i][1])) cntr++;
            if (cntr == 2) return true;
        }

        //Checking if the selection of 3 cards is either in a row or a column
        if (coord.length == 3 && (coord[0][0] == coord[1][0] - 1 && coord[1][0] == coord[2][0] - 1  //card1.x = card2.x-1 = card3.x-2
                || coord[0][1] == coord[1][1] - 1 && coord[1][1] == coord[2][1] - 1))            //card1.x = card2.x = card3.x
        {
            int cntr = 0;
            for (int i = 0; i < 3; i++) if (board.selectableCard(coord[i][0], coord[i][1])) cntr++;
            if (cntr == 3) return true;
        }

        //Checking if the selection of 2 cards is either in a row or a column
        if (coord.length == 2 && (coord[0][0] == coord[1][0] - 1         //card1.x = card2.x-1
                || coord[0][1] == coord[1][1] - 1))                   //card1.x = card2.x
        {
            int cntr = 0;
            for (int i = 0; i < 2; i++) if (board.selectableCard(coord[i][0], coord[i][1])) cntr++;
            if (cntr == 2) return true;
        }

        //Checking if a single card is selectable
        if (coord.length == 1) return board.selectableCard(coord[0][0], coord[0][1]);
        return false;
    }

    /**
     * Reorder the coordinates make them usable by canSelect()
     * @param array coordinate matrix
     * @return order by descendent x;
     */
    private int[][] coordOrder(int[][] array){
        int[] tmp;
        if(array.length == 3){
            if(array[0][0] == array[1][0]){                              //the x's coordinates are fixed
                tmp = new int[]{array[0][1], array[1][1], array[2][1]};  //y's coordinates are saved into tmp[3]
                Arrays.sort(tmp);                                        //method of Arrays class to sort array by desc
                for(int i = 0; i<3;i++){
                    array[i][1]=tmp[i];                                  //loop to save new ordered coordinates into array
                }
            }
            if(array[0][1] == array[1][1]){                              //same as before, but now the y's coordinates
                tmp = new int[]{array[0][0], array[1][0], array[2][0]};  //are fixed
                Arrays.sort(tmp);
                for(int i = 0; i<3; i++){
                    array[i][0]=tmp[i];
                }
            }
        }
        return array;
    }

    /**
     * Checks if coordinates of cards selected are diagonally arranged
     * @param x matrix of the coordinates
     * @return 'true' if they are not selectable (diagonal)
     */
    private boolean isDiagonal(int[][] x){
        if(x.length==2)return x[0][0] != x[1][0] && x[0][1] != x[1][1]; // check for two cards
        if(x.length==3)return x[0][0] != x[1][0] && x[0][1] != x[1][1] && x[1][0] != x[2][0] && x[1][1] != x[2][1]; //check for three cards
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

    /**
     * Checks if the insertion can actually be made
     * @param playerUsername of the player whose bookshelf to perform the insertion
     * @param column where the player wants to insert his cards
     * @param cardNumber number of cards to insert into the column
     * @return if the insertion is valid
     */
    public boolean canInsert(String playerUsername, int column, int cardNumber){
        //Checking if the selected row is an existing one
        if(column < 0 || column >= 5 || cardNumber < 1 || cardNumber > 3) return false;

        //Checking if the selected column has enough space for the number of cards selected
        return players.get(playerUsername).getBookshelf().getCell(cardNumber - 1, column).isCellEmpty();

    }

    //TODO: Metodo forwarding, revisionare
    /**
     * Inserts each selected card in order into the player's bookshelf
     * @param playerUsername player who makes the move
     * @param column into which the cards selected by the player are inserted
     * @param cards selected and arranged by the player in the desired order
     */
    public void addCardsToBookshelf(String playerUsername, int column, ArrayList<Card> cards) {
        for(Card c : cards) players.get(playerUsername).addCardToBookshelf(column, c);
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
     * Arranges the players by score
     * @return ordered TreeMap (username, score)
     */
    public TreeMap<String, Integer> orderByScore(){
        HashMap<String, Integer> hashmap = new HashMap<>();

        //for loop to fill hashmap
        for(String username: players.keySet())hashmap.put(username, getPlayer(username).getScore());

        //makes a custom comparator to sort by descending order
        Comparator<String> comparator = (score1, score2) -> {
            //order based on players's score
            return hashmap.get(score2).compareTo(hashmap.get(score1));
        };

        //treemap using personalized comparator (see upper lines)
        TreeMap<String, Integer> treeMap = new TreeMap<>(comparator);

        treeMap.putAll(hashmap);
        return treeMap;
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
    public void setCommonGoals(Goal[] x){
        commonGoals=x;
    }
    //endregion

}
