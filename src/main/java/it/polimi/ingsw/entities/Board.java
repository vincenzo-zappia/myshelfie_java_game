/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.3
 * Comments: using tree-like data structure, managed as matrix instead of array of lists.
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.exceptions.NoMoreCardsException;
import it.polimi.ingsw.util.BoardTile;

/**
 * Board of the game from where the players can pick the cards to add to their bookshelves
 */
public class Board {
    private final BoardTile[][] board;
    private final Bag bag;

    //region CONSTRUCTOR
    public Board(int playerNum){
        board = new BoardTile[9][9];

        //Initializing every single tile of the board (even the ones that should not be playable)
        for(int i = 0; i < 9; i++) for(int j = 0; j < 9; j++) board[i][j] = new BoardTile();

        //Activating every tile of the playable board given the number of participants
        initBoard();
        initBoard(playerNum);

        //Initialization of the bag with all the cards of the game
        bag = new Bag();

    }

    /**
     * Initializes the basic board by activating the tiles that do not depend on the number of players
     */
    private void initBoard(){
        for(int i = 3; i <= 4; i++) board[1][i].setTileActive();
        for(int i = 3; i <= 5; i++) board[2][i].setTileActive();
        for(int i = 4; i <= 5; i++) board[i][1].setTileActive();
        for(int i = 3; i <= 4; i++) board[i][7].setTileActive();
        for(int row = 3; row <= 5; row++){
            for(int col = 2; col<=6; col++) board[row][col].setTileActive();
        }
        for(int i = 3; i <= 5; i++) board[6][i].setTileActive();
        for(int i = 4; i <= 5; i++) board[7][i].setTileActive();
    }

    /**
     * Activates the additional tiles that depend on the number of players
     * @param playerNum number of players
     */
    private void initBoard(int playerNum){
        if(playerNum >= 3){
            board[0][3].setTileActive();
            board[2][2].setTileActive();
            board[2][6].setTileActive();
            board[3][8].setTileActive();
            board[5][0].setTileActive();
            board[6][2].setTileActive();
            board[6][6].setTileActive();
            board[8][5].setTileActive();
        }
        if (playerNum == 4) {
            board[0][4].setTileActive();
            board[1][5].setTileActive();
            board[3][1].setTileActive();
            board[4][0].setTileActive();
            board[4][8].setTileActive();
            board[5][7].setTileActive();
            board[7][3].setTileActive();
            board[8][4].setTileActive();
        }
    }

    //TODO Gestire il riempimento a bag quasi vuota (con isBagEmpty() non dovrebbe mai arrivare a lanciare l'eccezione?)
    //TODO renderlo booleano?
    /**
     * Fills the board either after the creation of a new board or when a player cannot select more than one card
     */
    public void fillBoard(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                try {
                    if(board[i][j].isTileActive() && board[i][j].isTileEmpty() && !bag.isBagEmpty()) board[i][j].setCard(bag.drawCard());
                } catch (NoMoreCardsException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    //endregion

    //region METHODS
    /**
     * Checks whether a card is selectable
     * @param row of the tile to check
     * @param column of the tile to check
     * @return if the card is selectable
     */
    public boolean selectableCard(int row, int column){

        //Checking if the received tile coordinates exceed the board boundaries
        if(row < 0 || row > 8 || column < 0 || column > 8) return false;

        //Checking whether the selected tile is already empty or not part of the playable board
        if (board[row][column].isTileActive() && !board[row][column].isTileEmpty()) {

            //Checking whether tile below actual one is empty or not active
            if (row > 0 && board[row - 1][column].isTileEmpty() || !board[row - 1][column].isTileActive()) return true;

            //Checking whether tile above actual one is empty or not active
            if (row < 8 && board[row + 1][column].isTileEmpty() || !board[row + 1][column].isTileActive()) return true;

            //Checking whether tile on left is empty or not active
            if (column > 0 && board[row][column - 1].isTileEmpty() || !board[row][column - 1].isTileActive()) return true;

            //Checking whether tile on right is empty or not active
            if (column < 8 && board[row][column + 1].isTileEmpty() || !board[row][column + 1].isTileActive()) return true;
        }
        return false;
    }

    /**
     * Removes a card from the board
     * @param row of the card tile
     * @param column of the card tile
     * @return the removed card
     */
    public Card removeCard(int row, int column){

        //Extracting the actual card from its coordinates
        Card card;
        try {
            card = board[row][column].getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        //Setting the card tile to empty after the removal
        board[row][column].setTileEmpty();
        return card;
    }
    //endregion

    //region GETTER AND SETTER
    public BoardTile[][] getBoard(){
        return board;
    }
    public BoardTile getBoardTile(int row, int column){
        return getBoard()[row][column];
    }
    public Card getCard(int row, int column){
        try {
            return getBoardTile(row, column).getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

}
