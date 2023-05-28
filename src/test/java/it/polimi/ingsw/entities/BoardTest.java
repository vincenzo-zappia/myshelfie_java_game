package it.polimi.ingsw.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    //region SETUP
    @Test
    void twoPlayersSetup(){
        board = new Board(2);

        //assert true that the base board tiles are correctly activated
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[1][i].isTileActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[2][i].isTileActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[i][1].isTileActive());
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[i][7].isTileActive());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getBoard()[row][col].isTileActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[6][i].isTileActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[7][i].isTileActive());

        //assert false for the tiles that should be active only if the number of player exceeds 3
        assertFalse(board.getBoard()[0][3].isTileActive());
        assertFalse(board.getBoard()[2][2].isTileActive());
        assertFalse(board.getBoard()[2][6].isTileActive());
        assertFalse(board.getBoard()[3][8].isTileActive());
        assertFalse(board.getBoard()[5][0].isTileActive());
        assertFalse(board.getBoard()[6][2].isTileActive());
        assertFalse(board.getBoard()[6][6].isTileActive());
        assertFalse(board.getBoard()[8][5].isTileActive());

        //assert false for the tiles that should be active only if the number of player is 4
        assertFalse(board.getBoard()[0][4].isTileActive());
        assertFalse(board.getBoard()[1][5].isTileActive());
        assertFalse(board.getBoard()[3][1].isTileActive());
        assertFalse(board.getBoard()[4][0].isTileActive());
        assertFalse(board.getBoard()[4][8].isTileActive());
        assertFalse(board.getBoard()[5][7].isTileActive());
        assertFalse(board.getBoard()[7][3].isTileActive());
        assertFalse(board.getBoard()[8][4].isTileActive());
    }
    @Test
    void threePlayersSetup(){
        board = new Board(3);

        //assert true that the base board tiles are correctly activated
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[1][i].isTileActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[2][i].isTileActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[i][1].isTileActive());
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[i][7].isTileActive());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getBoard()[row][col].isTileActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[6][i].isTileActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[7][i].isTileActive());

        //assert true for the tiles that should be active only if the number of player exceeds 3
        assertTrue(board.getBoard()[0][3].isTileActive());
        assertTrue(board.getBoard()[2][2].isTileActive());
        assertTrue(board.getBoard()[2][6].isTileActive());
        assertTrue(board.getBoard()[3][8].isTileActive());
        assertTrue(board.getBoard()[5][0].isTileActive());
        assertTrue(board.getBoard()[6][2].isTileActive());
        assertTrue(board.getBoard()[6][6].isTileActive());
        assertTrue(board.getBoard()[8][5].isTileActive());

        //assert false for the tiles that should be active only if the number of player is 4
        assertFalse(board.getBoard()[0][4].isTileActive());
        assertFalse(board.getBoard()[1][5].isTileActive());
        assertFalse(board.getBoard()[3][1].isTileActive());
        assertFalse(board.getBoard()[4][0].isTileActive());
        assertFalse(board.getBoard()[4][8].isTileActive());
        assertFalse(board.getBoard()[5][7].isTileActive());
        assertFalse(board.getBoard()[7][3].isTileActive());
        assertFalse(board.getBoard()[8][4].isTileActive());
    }
    @Test
    void fourPlayersSetup(){
        board = new Board(4);

        //assert true that the base board tiles are correctly activated
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[1][i].isTileActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[2][i].isTileActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[i][1].isTileActive());
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[i][7].isTileActive());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getBoard()[row][col].isTileActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[6][i].isTileActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[7][i].isTileActive());

        //assert true for the tiles that should be active only if the number of player exceeds 3
        assertTrue(board.getBoard()[0][3].isTileActive());
        assertTrue(board.getBoard()[2][2].isTileActive());
        assertTrue(board.getBoard()[2][6].isTileActive());
        assertTrue(board.getBoard()[3][8].isTileActive());
        assertTrue(board.getBoard()[5][0].isTileActive());
        assertTrue(board.getBoard()[6][2].isTileActive());
        assertTrue(board.getBoard()[6][6].isTileActive());
        assertTrue(board.getBoard()[8][5].isTileActive());

        //assert false for the tiles that should be active only if the number of player is 4
        assertTrue(board.getBoard()[0][4].isTileActive());
        assertTrue(board.getBoard()[1][5].isTileActive());
        assertTrue(board.getBoard()[3][1].isTileActive());
        assertTrue(board.getBoard()[4][0].isTileActive());
        assertTrue(board.getBoard()[4][8].isTileActive());
        assertTrue(board.getBoard()[5][7].isTileActive());
        assertTrue(board.getBoard()[7][3].isTileActive());
        assertTrue(board.getBoard()[8][4].isTileActive());
    }
    //endregion

    //region EMPTY
    @Test
    void twoPlayersBoardIsEmpty(){
        board = new Board(2);

        //filling the board
        board.fillBoard();

        //emptying the board
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board.getBoardTile(i, j).isTileActive()) if(!board.getBoardTile(i, j).isTileEmpty()) board.removeCard(i, j);
            }
        }

        //checking if the board was actually emptied
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[1][i].isTileEmpty());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[2][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[i][1].isTileEmpty());
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[i][7].isTileEmpty());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getBoard()[row][col].isTileEmpty());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[6][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[7][i].isTileEmpty());
    }
    @Test
    void threePlayersBoardIsEmpty(){
        board = new Board(3);
        board.fillBoard();

        //emptying the board
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board.getBoardTile(i, j).isTileActive()) if(!board.getBoardTile(i, j).isTileEmpty()) board.removeCard(i, j);
            }
        }

        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[1][i].isTileEmpty());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[2][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[i][1].isTileEmpty());
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[i][7].isTileEmpty());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getBoard()[row][col].isTileEmpty());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[6][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[7][i].isTileEmpty());

        assertTrue(board.getBoard()[0][3].isTileEmpty());
        assertTrue(board.getBoard()[2][2].isTileEmpty());
        assertTrue(board.getBoard()[2][6].isTileEmpty());
        assertTrue(board.getBoard()[3][8].isTileEmpty());
        assertTrue(board.getBoard()[5][0].isTileEmpty());
        assertTrue(board.getBoard()[6][2].isTileEmpty());
        assertTrue(board.getBoard()[6][6].isTileEmpty());
        assertTrue(board.getBoard()[8][5].isTileEmpty());

    }
    @Test
    void fourPlayersBoardIsEmpty(){
        board = new Board(4);
        board.fillBoard();

        //emptying the board
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(board.getBoardTile(i, j).isTileActive()) if(!board.getBoardTile(i, j).isTileEmpty()) board.removeCard(i, j);
            }
        }

        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[1][i].isTileEmpty());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[2][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[i][1].isTileEmpty());
        for(int i = 3; i<=4;i++) assertTrue(board.getBoard()[i][7].isTileEmpty());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getBoard()[row][col].isTileEmpty());
        for(int i = 3; i<=5;i++) assertTrue(board.getBoard()[6][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertTrue(board.getBoard()[7][i].isTileEmpty());

        assertTrue(board.getBoard()[0][3].isTileEmpty());
        assertTrue(board.getBoard()[2][2].isTileEmpty());
        assertTrue(board.getBoard()[2][6].isTileEmpty());
        assertTrue(board.getBoard()[3][8].isTileEmpty());
        assertTrue(board.getBoard()[5][0].isTileEmpty());
        assertTrue(board.getBoard()[6][2].isTileEmpty());
        assertTrue(board.getBoard()[6][6].isTileEmpty());
        assertTrue(board.getBoard()[8][5].isTileEmpty());

        assertTrue(board.getBoard()[0][4].isTileEmpty());
        assertTrue(board.getBoard()[1][5].isTileEmpty());
        assertTrue(board.getBoard()[3][1].isTileEmpty());
        assertTrue(board.getBoard()[4][0].isTileEmpty());
        assertTrue(board.getBoard()[4][8].isTileEmpty());
        assertTrue(board.getBoard()[5][7].isTileEmpty());
        assertTrue(board.getBoard()[7][3].isTileEmpty());
        assertTrue(board.getBoard()[8][4].isTileEmpty());

    }
    //endregion

    //region FULL
    @Test
    void twoPlayersBoardIsFull(){
        board = new Board(2);
        board.fillBoard();
        for(int i = 3; i<=4;i++) assertFalse(board.getBoard()[1][i].isTileEmpty());
        for(int i = 3; i<=5;i++) assertFalse(board.getBoard()[2][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertFalse(board.getBoard()[i][1].isTileEmpty());
        for(int i = 3; i<=4;i++) assertFalse(board.getBoard()[i][7].isTileEmpty());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertFalse(board.getBoard()[row][col].isTileEmpty());
        for(int i = 3; i<=5;i++) assertFalse(board.getBoard()[6][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertFalse(board.getBoard()[7][i].isTileEmpty());
    }
    @Test
    void threePlayersBoardIsFull(){
        board = new Board(3);
        board.fillBoard();
        for(int i = 3; i<=4;i++) assertFalse(board.getBoard()[1][i].isTileEmpty());
        for(int i = 3; i<=5;i++) assertFalse(board.getBoard()[2][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertFalse(board.getBoard()[i][1].isTileEmpty());
        for(int i = 3; i<=4;i++) assertFalse(board.getBoard()[i][7].isTileEmpty());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertFalse(board.getBoard()[row][col].isTileEmpty());
        for(int i = 3; i<=5;i++) assertFalse(board.getBoard()[6][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertFalse(board.getBoard()[7][i].isTileEmpty());

        assertFalse(board.getBoard()[0][3].isTileEmpty());
        assertFalse(board.getBoard()[2][2].isTileEmpty());
        assertFalse(board.getBoard()[2][6].isTileEmpty());
        assertFalse(board.getBoard()[3][8].isTileEmpty());
        assertFalse(board.getBoard()[5][0].isTileEmpty());
        assertFalse(board.getBoard()[6][2].isTileEmpty());
        assertFalse(board.getBoard()[6][6].isTileEmpty());
        assertFalse(board.getBoard()[8][5].isTileEmpty());
    }
    @Test
    void fourPlayersBoardIsFull(){
        board = new Board(4);
        board.fillBoard();
        for(int i = 3; i<=4;i++) assertFalse(board.getBoard()[1][i].isTileEmpty());
        for(int i = 3; i<=5;i++) assertFalse(board.getBoard()[2][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertFalse(board.getBoard()[i][1].isTileEmpty());
        for(int i = 3; i<=4;i++) assertFalse(board.getBoard()[i][7].isTileEmpty());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertFalse(board.getBoard()[row][col].isTileEmpty());
        for(int i = 3; i<=5;i++) assertFalse(board.getBoard()[6][i].isTileEmpty());
        for(int i = 4; i<=5;i++) assertFalse(board.getBoard()[7][i].isTileEmpty());

        assertFalse(board.getBoard()[0][3].isTileEmpty());
        assertFalse(board.getBoard()[2][2].isTileEmpty());
        assertFalse(board.getBoard()[2][6].isTileEmpty());
        assertFalse(board.getBoard()[3][8].isTileEmpty());
        assertFalse(board.getBoard()[5][0].isTileEmpty());
        assertFalse(board.getBoard()[6][2].isTileEmpty());
        assertFalse(board.getBoard()[6][6].isTileEmpty());
        assertFalse(board.getBoard()[8][5].isTileEmpty());

        assertFalse(board.getBoard()[0][4].isTileEmpty());
        assertFalse(board.getBoard()[1][5].isTileEmpty());
        assertFalse(board.getBoard()[3][1].isTileEmpty());
        assertFalse(board.getBoard()[4][0].isTileEmpty());
        assertFalse(board.getBoard()[4][8].isTileEmpty());
        assertFalse(board.getBoard()[5][7].isTileEmpty());
        assertFalse(board.getBoard()[7][3].isTileEmpty());
        assertFalse(board.getBoard()[8][4].isTileEmpty());
    }
    //endregion

    @Test
    void removeCard(){
        board = new Board(2); //Initializing empty board
        assertTrue(board.getBoardTile(1, 3).isTileEmpty()); //Asserting a tile is empty
        assertFalse(board.selectableCard(1, 3)); //Asserting the card in an empty tile is not selectable
        board.fillBoard(); //Filling the board
        assertFalse(board.getBoardTile(1, 3).isTileEmpty()); //Asserting the tile is full
        assertTrue(board.selectableCard(1, 3)); //Asserting the card of a full tile is selectable
        assertEquals(board.getCard(1, 3), board.removeCard(1, 3)); //Checking if the card removed from the tile is the same one that there was in that tile
        assertTrue(board.getBoardTile(1, 3).isTileEmpty()); //Checking if the tile was emptied
        assertFalse(board.selectableCard(1, 3)); //Checking that the now empty tile has no selectable card in it

    }

}