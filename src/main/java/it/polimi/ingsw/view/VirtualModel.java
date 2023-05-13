package it.polimi.ingsw.view;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;

public class VirtualModel {

    //region ATTRIBUTES
    private BoardCell[][] board;
    private Cell[][] bookshelf;
    private Goal[] commonGoals;
    private PrivateGoal privateGoal;
    private int[][] coordinates;
    private boolean selectionMade;
    //endregion

    //region CONSTRUCTOR
    public VirtualModel(){
        //Board initialization
        board = new BoardCell[9][9];

        //Bookshelf initialization
        bookshelf = new Cell[6][5];
        for(int i=0; i<6;i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Cell();
    }
    //endregion

    //region GETTER AND SETTER
    public void setCoordinates(int[][] coordinates) {
        this.coordinates = coordinates;
    }
    public int[][] getCoordinates() {
        return coordinates;
    }
    public void setSelectionMade(boolean bool){
        selectionMade = bool;
    }
    public boolean isSelectionMade() {
        return selectionMade;
    }
    public void setBoard(BoardCell[][] newBoard) {
        this.board = newBoard;
    }
    public BoardCell[][] getBoard() {
        return board;
    }
    public void setBookshelf(Cell[][] newBookshelf) {
        this.bookshelf = newBookshelf;
    }
    public Cell[][] getBookshelf() {
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
    //endregion

    public void refreshBoard(int[][] coordinates) {
        for (int[] coordinate : coordinates) {
            int row = coordinate[0];
            int column = coordinate[1];
            board[row][column].setCellEmpty();
        }
    }

}
