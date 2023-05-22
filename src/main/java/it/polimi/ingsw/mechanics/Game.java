/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 21/03/2023
 * IDE: IntelliJ IDEA
 * Version 1.0
 * Comments: none
 */

package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.*;
import it.polimi.ingsw.entities.goals.*;
import it.polimi.ingsw.entities.util.BoardTile;

import java.util.*;

import it.polimi.ingsw.entities.util.SerializableTreeMap;
import it.polimi.ingsw.entities.util.Tile;

public class Game{

    //region ATTRIBUTES
    private final Board board;
    private final HashMap<String, Player> players;
    private Goal[] commonGoals;
    private final CommonGoal0 commonGoal0;
    //endregion

    public Game(ArrayList<String> usernames){

        //Creating the board and filling it with a first batch of cards
        board = new Board(usernames.size());
        board.fillBoard();

        //Creating the players as entities of the game from the lobby username list
        players = new HashMap<>();
        PrivateGoalFactory privateGoalFactory = new PrivateGoalFactory();
        for(String user: usernames)players.put(user, new Player(user, privateGoalFactory.makePrivateGoal()));

        //Randomly picking the common goals of the game
        CommonGoalFactory factory = new CommonGoalFactory();
        commonGoals = factory.makeCommonGoal();
        commonGoal0 = new CommonGoal0();

    }

    //region METHODS
    /**
     * Checks if the selected cards are selectable
     * @param coord coordinates of the selected cards:
     *              coord[x][0] = row
     *              coord[x][1] = column
     * @return if the selection is valid
     */
    public boolean canSelect(String playerUsername, int[][] coord){

        //Checking if the player has selected more cards than they can insert into their bookshelf
        Bookshelf bookshelf = players.get(playerUsername).getBookshelf();
        boolean sentinel = false;
        for(int i = 0; i < 5; i++) if(6 - bookshelf.cardsInColumn(i) >= coord.length){
            sentinel = true;
            break;
        }
        if (!sentinel) return false;

        //TODO: Vedere se funziona funzionale
        /*
        int maxCards = Arrays.stream(coord).mapToInt(row -> 6 - bookshelf.cardsInColumn(row[0])).max().orElse(0);
        if (maxCards < coord.length) {
            return false;
        }
         */

        //Checking if any of the coordinates exceed the board dimensions
        for (int[] row : coord) for (int col : row) if (col < 0 || col > 8) return false;

        //Checking if a single card is selectable
        if (coord.length == 1) return board.selectableCard(coord[0][0], coord[0][1]);

        //Checking if the selection forms a continuous row or column
        boolean isRow = true;
        boolean isColumn = true;
        int firstRow = coord[0][0];
        int firstCol = coord[0][1];

        for (int[] cell : coord) {
            if (cell[0] != firstRow) {
                isRow = false;
            }
            if (cell[1] != firstCol) {
                isColumn = false;
            }
        }

        if (!isRow && !isColumn) {
            return false;
        }

        if (isRow) {
            Arrays.sort(coord, (a, b) -> Integer.compare(a[1], b[1]));
            for (int i = 0; i < coord.length - 1; i++) {
                if (coord[i][1] + 1 != coord[i + 1][1]) {
                    return false;
                }
            }
        }

        if (isColumn) {
            Arrays.sort(coord, (a, b) -> Integer.compare(a[0], b[0]));
            for (int i = 0; i < coord.length - 1; i++) {
                if (coord[i][0] + 1 != coord[i + 1][0]) {
                    return false;
                }
            }
        }

        //Checking if the cards are selectable by game rules
        if (coord.length <= 3) {
            for (int[] cell : coord) {
                if (!board.selectableCard(cell[0], cell[1])) {
                    return false;
                }
            }
            return true;
        }

        return false;

    }

    /**
     * Removes at most three cards from the board
     * @param coordinates of the cards to be removed
     * @return the removed cards
     */
    public ArrayList<Card> removeSelectedCards(int[][] coordinates){
        ArrayList<Card> removedCards = new ArrayList<>();
        for (int[] coordinate : coordinates) removedCards.add(board.removeCard(coordinate[0], coordinate[1]));
        return removedCards;
    }

    /**
     * Checks if the selected cards can be added to the player's bookshelf
     * @param playerUsername of the player who wants to add the cards
     * @param column where the player wants to insert the cards
     * @param cardNumber number of cards to insert into the column
     * @return if the insertion is valid
     */
    public boolean canInsert(String playerUsername, int column, int cardNumber){

        //Checking if the selected row is an existing one
        if(column < 0 || column >= 5 || cardNumber < 1 || cardNumber > 3) return false;

        //Checking if the selected column has enough space for the number of cards selected
        return players.get(playerUsername).getBookshelf().getBookshelfTile(cardNumber - 1, column).isTileEmpty();

    }

    /**
     * Adds the selected cards into the player's bookshelf
     * @param playerUsername player who makes the move
     * @param column where the selected cards are added
     * @param cards the previously selected cards
     */
    public void addCardsToBookshelf(String playerUsername, int column, ArrayList<Card> cards) {
        for(Card c : cards) players.get(playerUsername).getBookshelf().addCard(column, c);
    }

    /**
     * Checks is the bookshelf of a given player is full
     * @param username player whose bookshelf to check
     * @return if the bookshelf of the player is full
     */
    public boolean isPlayerBookshelfFull(String username){
        return players.get(username).getBookshelf().isBookshelfFull();
    }

    /**
     * Checks if the board has no more selectable cards
     * @return if every card doesn't have another adjacent card
     */
    public boolean checkRefill(){
        BoardTile[][] matrix = board.getBoard();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(!matrix[i][j].isTileActive()) continue;
                if((!matrix[i][j].isTileEmpty() && !matrix[i + 1][j].isTileEmpty())
                        || (!matrix[i][j].isTileEmpty() && !matrix[i][j + 1].isTileEmpty())) return false;
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
     * Checks the progress of the private goal achievement of every player
     */
    public void scorePrivateGoal(){
        for(Player p : players.values()){
            p.addScore(p.getPrivateGoal().checkGoal(p.getBookshelf()));
            p.addScore(commonGoal0.checkGoal(p.getBookshelf()));
        }
    }

    /**
     * Arranges the players by score
     * @return ordered SerializableTreeMap (username, score)
     */
    public SerializableTreeMap<String, Integer> orderByScore(){
        HashMap<String, Integer> hashmap = new HashMap<>();

        //For loop to fill hashmap
        for(String username: players.keySet())hashmap.put(username, getPlayer(username).getScore());

        //Making a custom comparator to sort the players by descending order by score
        Comparator<String> comparator = (score1, score2) -> hashmap.get(score2).compareTo(hashmap.get(score1));

        //Creating a treemap using a personalized comparator (see code above)
        SerializableTreeMap<String, Integer> treeMap = new SerializableTreeMap<>(comparator);
        treeMap.putAll(hashmap);
        return treeMap;
    }
    //endregion

    //region UTIL
    /**
     * Rearranges the coordinates to make them usable by canSelect()
     * @param coordinates to sort
     * @return coordinates sorted by descending order
     */
    private int[][] sortCoordinates(int[][] coordinates){
        int[] tmp;

        if(coordinates.length == 3){
            if(coordinates[0][0] == coordinates[1][0]){                              //the x's coordinates are fixed
                tmp = new int[]{coordinates[0][1], coordinates[1][1], coordinates[2][1]};  //y's coordinates are saved into tmp[3]

                //Sorting the coordinates by descending order and saving them into the array
                Arrays.sort(tmp);
                for(int i = 0; i < 3; i++){
                    coordinates[i][1] = tmp[i];
                }
            }
            if(coordinates[0][1] == coordinates[1][1]){                              //same as before, but now the y's coordinates
                tmp = new int[]{coordinates[0][0], coordinates[1][0], coordinates[2][0]};  //are fixed
                Arrays.sort(tmp);
                for(int i = 0; i < 3; i++){
                    coordinates[i][0] = tmp[i];
                }
            }
        }
        return coordinates;
    }

    /**
     * Checks if the selected cards are in a diagonal
     * @param coordinates of the selected cards
     * @return if the cards are diagonally arranged
     */
    private boolean isDiagonal(int[][] coordinates){

        //Checking relatively to the number of cards selected
        switch (coordinates.length){
            case 2 -> { return coordinates[0][0] != coordinates[1][0] && coordinates[0][1] != coordinates[1][1]; }
            case 3 -> { return coordinates[0][0] != coordinates[1][0] && coordinates[0][1] != coordinates[1][1] && coordinates[1][0] != coordinates[2][0] && coordinates[1][1] != coordinates[2][1]; }
        }
        return false;

    }
    //endregion

    //region GETTER AND SETTER
    public ArrayList<Player> getPlayers(){
        return new ArrayList<>(players.values());
    }
    public Player getPlayer(String username){
        return players.get(username);
    }
    public Tile[][] getPlayerBookshelf(String username){
        return players.get(username).getBookshelf().getBookshelf();
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
