package it.polimi.ingsw.view;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;

//TODO: Non serve in GUI(?)
/**
 * Client side abstraction of the game entities
 */
public class VirtualModel {

    //region ATTRIBUTES
    private BoardTile[][] board;
    private Tile[][] bookshelf;
    private Goal[] commonGoals;
    private PrivateGoal privateGoal;
    private int[][] coordinates;
    private boolean endGame;
    //endregion

    //region CONSTRUCTOR
    public VirtualModel(){
        //Board initialization
        board = new BoardTile[9][9];

        //Bookshelf initialization
        bookshelf = new Tile[6][5];
        for(int i=0; i<6;i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Tile();

        endGame = false;

    }
    //endregion

    //region METHODS
    /**
     * Removes the cards selected by the player after their insertion
     * @param coordinates of the cards to remove
     */
    public void refreshBoard(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            int row = coordinate[0];
            int column = coordinate[1];
            board[row][column].setTileEmpty();
        }
    }
    //endregion

    //region GETTER AND SETTER
    public void setCoordinates(int[][] coordinates) {
        this.coordinates = coordinates;
    }
    public int[][] getCoordinates() {
        return coordinates;
    }
    public void setBoard(BoardTile[][] newBoard) {
        this.board = newBoard;
    }
    public BoardTile[][] getBoard() {
        return board;
    }
    public void setBookshelf(Tile[][] newBookshelf) {
        this.bookshelf = newBookshelf;
    }
    public Tile[][] getBookshelf() {
        return bookshelf;
    }
    public void setCommonGoals(Goal[] commonGoals) {
        this.commonGoals = commonGoals;
    }
    public Goal[] getCommonGoals() {
        return commonGoals;
    }
    public void setPrivateGoal(PrivateGoal privateGoal) {
        this.privateGoal = privateGoal;
    }
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }
    public void setEndGame(boolean status){
        endGame = status;
    }
    public boolean getEndGame(){
        return endGame;
    }
    //endregion

}
