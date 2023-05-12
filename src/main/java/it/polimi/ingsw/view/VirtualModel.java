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
    private int[][] selection;
    private boolean selectionUpdated;
    //endregion

    //region CONSTRUCTOR
    public VirtualModel(){
        //inizialize board
        board = new BoardCell[9][9];


        //inizialize bookshelf
        bookshelf = new Cell[6][5];
        for(int i=0; i<6;i++) for(int j=0; j<5; j++) bookshelf[i][j] = new Cell();
    }
    //endregion

    //region GETTER AND SETTER
    public void setSelection(int[][] coordinates) {
        this.selection = coordinates;
        selectionUpdated = true;
    }
    public int[][] getSelection() {
        selectionUpdated = false;
        return selection;
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

    public boolean isSelectionUpdated() {
        return selectionUpdated;
    }

}
