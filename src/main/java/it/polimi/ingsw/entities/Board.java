/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: using tree-like data structure, managed as matrix instead of array of lists.
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.exceptions.NoMoreCardsException;
import it.polimi.ingsw.util.BoardCell;

import java.io.Serializable;

public class Board {

    //region ATTRIBUTES
    private final BoardCell[][] matrix;
    private final Bag bag;
    //endregion

    //region CONSTRUCTOR
    public Board(int playerNum){
    
        //initialization of data structure that represents table
        matrix = new BoardCell[9][9];
        for(int i = 0; i<9; i++) for(int j=0;j<9;j++) matrix[i][j] = new BoardCell();
        initBoard();
        initBoard(playerNum);
        //initialization of the bag with all the cards in the game
        bag = new Bag();

    }

    /**
     * Activates the board cells that do NOT depend on the number of players (basic board)
     */
    private void initBoard(){
        for(int i = 3; i<=4;i++)matrix[1][i].setCellActive();
        for(int i = 3; i<=5;i++)matrix[2][i].setCellActive();
        for(int i = 4; i<=5;i++)matrix[i][1].setCellActive();
        for(int i = 3; i<=4;i++)matrix[i][7].setCellActive();
        for(int row = 3; row<=5;row++){
            for(int col=2; col<=6; col++)matrix[row][col].setCellActive();
        }
        for(int i = 3; i<=5;i++)matrix[6][i].setCellActive();
        for(int i = 4; i<=5;i++)matrix[7][i].setCellActive();
    }

    /**
     * Activates the additional board cells that depend on the number of players
     * @param x number of players
     */
    private void initBoard(int x){
        if(x >= 3){
            matrix[0][3].setCellActive();
            matrix[2][2].setCellActive();
            matrix[2][6].setCellActive();
            matrix[3][8].setCellActive();
            matrix[5][0].setCellActive();
            matrix[6][2].setCellActive();
            matrix[6][6].setCellActive();
            matrix[8][5].setCellActive();
        }
        if (x == 4) {
            matrix[0][4].setCellActive();
            matrix[1][5].setCellActive();
            matrix[3][1].setCellActive();
            matrix[4][0].setCellActive();
            matrix[4][8].setCellActive();
            matrix[5][7].setCellActive();
            matrix[7][3].setCellActive();
            matrix[8][4].setCellActive();
        }
    }

    /**
     * Fills the board. It's called in two occasions:
     *  1) when the board is created
     *  2) when a player cannot select more than 1 or 2 cards
     */
    public void fillBoard(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                try {
                    if(matrix[i][j].isCellActive() && matrix[i][j].isCellEmpty() && !bag.isBagEmpty()) matrix[i][j].setCard(bag.drawCard()); //added check if bag is empty
                } catch (NoMoreCardsException e) {
                    throw new RuntimeException(e);
                }
                //TODO add a finally branch to manage the exception
            }
        }
    }
    //endregion

    //region METHODS
    /**
     * Checks if a single card is selectable
     * @param x row of the card
     * @param y column of the card
     * @return if the card is selectable
     */
    public boolean selectableCard(int x, int y){
        /*
        if(matrix[x][y].isCellActive() && !matrix[x][y].isCellEmpty()){
            if((y < 8) && (matrix[x][y+1].isCellEmpty() || !matrix[x][y+1].isCellActive())) return true;
            if((x > 0) && (matrix[x-1][y].isCellEmpty() || !matrix[x-1][y].isCellActive())) return true;
            if((x < 8) && (matrix[x+1][y].isCellEmpty() || !matrix[x+1][y].isCellActive())) return true;
            if((y > 0) && (matrix[x][y-1].isCellEmpty() || !matrix[x][y-1].isCellActive())) return true;
            return false;
        }
        return false;
         */

        if(x<0 || x>8 || y<0 || y>8)return false;
        if (matrix[x][y].isCellActive() && !matrix[x][y].isCellEmpty()) {
            // Controllo dei lati
            if (x > 0 && matrix[x - 1][y].isCellEmpty() || !matrix[x - 1][y].isCellActive()) {
                 return true;
            }
            if (x < 8 && matrix[x + 1][y].isCellEmpty() || !matrix[x + 1][y].isCellActive()) {
                 return true;
            }
            if (y > 0 && matrix[x][y - 1].isCellEmpty() || !matrix[x][y - 1].isCellActive()) {
                 return true;
            }
            if (y < 8 && matrix[x][y + 1].isCellEmpty() || !matrix[x][y + 1].isCellActive()) {
                 return true;
            }
        }
        return false;
    }

    /**
     * Removes a card from the board
     * @param row of the card
     * @param column of the card
     * @return removed card
     */
    public Card removeCard(int row, int column){
        Card card;
        try {
            card = matrix[row][column].getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }
        matrix[row][column].setCellEmpty();
        return card;
    }


    //endregion

    //region GETTER AND SETTER
    public BoardCell[][] getMatrix(){
        return matrix;
    }
    public BoardCell getBoardCell (int row, int column){
        return getMatrix()[row][column];
    }
    public Card getCard(int row, int column){
        try {
            return getBoardCell(row, column).getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

}
