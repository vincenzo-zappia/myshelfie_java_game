package it.polimi.ingsw.server.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;

    //testing that the board is correctly set up for every kind of game in terms of the number of players
    //TODO: isolating the cells that always have to be inactive and asserting false on their activation
    @Test
    void twoPlayersSetup(){
        board = new Board(2);

        //assert true that the base board tiles are correctly activated
        for(int i = 3; i<=4;i++) assertTrue(board.getMatrix()[1][i].isCellActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getMatrix()[2][i].isCellActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getMatrix()[i][1].isCellActive());
        for(int i = 3; i<=4;i++) assertTrue(board.getMatrix()[i][7].isCellActive());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getMatrix()[row][col].isCellActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getMatrix()[6][i].isCellActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getMatrix()[7][i].isCellActive());

        //assert false for the tiles that should be active only if the number of player exceeds 3
        assertFalse(board.getMatrix()[0][3].isCellActive());
        assertFalse(board.getMatrix()[2][2].isCellActive());
        assertFalse(board.getMatrix()[2][6].isCellActive());
        assertFalse(board.getMatrix()[3][8].isCellActive());
        assertFalse(board.getMatrix()[5][0].isCellActive());
        assertFalse(board.getMatrix()[6][2].isCellActive());
        assertFalse(board.getMatrix()[6][6].isCellActive());
        assertFalse(board.getMatrix()[8][5].isCellActive());

        //assert false for the tiles that should be active only if the number of player is 4
        assertFalse(board.getMatrix()[0][4].isCellActive());
        assertFalse(board.getMatrix()[1][5].isCellActive());
        assertFalse(board.getMatrix()[3][1].isCellActive());
        assertFalse(board.getMatrix()[4][0].isCellActive());
        assertFalse(board.getMatrix()[4][8].isCellActive());
        assertFalse(board.getMatrix()[5][7].isCellActive());
        assertFalse(board.getMatrix()[7][3].isCellActive());
        assertFalse(board.getMatrix()[8][4].isCellActive());
    }
    @Test
    void threePlayersSetup(){
        board = new Board(3);

        //assert true that the base board tiles are correctly activated
        for(int i = 3; i<=4;i++) assertTrue(board.getMatrix()[1][i].isCellActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getMatrix()[2][i].isCellActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getMatrix()[i][1].isCellActive());
        for(int i = 3; i<=4;i++) assertTrue(board.getMatrix()[i][7].isCellActive());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getMatrix()[row][col].isCellActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getMatrix()[6][i].isCellActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getMatrix()[7][i].isCellActive());

        //assert true for the tiles that should be active only if the number of player exceeds 3
        assertTrue(board.getMatrix()[0][3].isCellActive());
        assertTrue(board.getMatrix()[2][2].isCellActive());
        assertTrue(board.getMatrix()[2][6].isCellActive());
        assertTrue(board.getMatrix()[3][8].isCellActive());
        assertTrue(board.getMatrix()[5][0].isCellActive());
        assertTrue(board.getMatrix()[6][2].isCellActive());
        assertTrue(board.getMatrix()[6][6].isCellActive());
        assertTrue(board.getMatrix()[8][5].isCellActive());

        //assert false for the tiles that should be active only if the number of player is 4
        assertFalse(board.getMatrix()[0][4].isCellActive());
        assertFalse(board.getMatrix()[1][5].isCellActive());
        assertFalse(board.getMatrix()[3][1].isCellActive());
        assertFalse(board.getMatrix()[4][0].isCellActive());
        assertFalse(board.getMatrix()[4][8].isCellActive());
        assertFalse(board.getMatrix()[5][7].isCellActive());
        assertFalse(board.getMatrix()[7][3].isCellActive());
        assertFalse(board.getMatrix()[8][4].isCellActive());
    }
    @Test
    void fourPlayersSetup(){
        board = new Board(4);

        //assert true that the base board tiles are correctly activated
        for(int i = 3; i<=4;i++) assertTrue(board.getMatrix()[1][i].isCellActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getMatrix()[2][i].isCellActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getMatrix()[i][1].isCellActive());
        for(int i = 3; i<=4;i++) assertTrue(board.getMatrix()[i][7].isCellActive());
        for(int row = 3; row<=5;row++) for(int col=2; col<=6; col++) assertTrue(board.getMatrix()[row][col].isCellActive());
        for(int i = 3; i<=5;i++) assertTrue(board.getMatrix()[6][i].isCellActive());
        for(int i = 4; i<=5;i++) assertTrue(board.getMatrix()[7][i].isCellActive());

        //assert true for the tiles that should be active only if the number of player exceeds 3
        assertTrue(board.getMatrix()[0][3].isCellActive());
        assertTrue(board.getMatrix()[2][2].isCellActive());
        assertTrue(board.getMatrix()[2][6].isCellActive());
        assertTrue(board.getMatrix()[3][8].isCellActive());
        assertTrue(board.getMatrix()[5][0].isCellActive());
        assertTrue(board.getMatrix()[6][2].isCellActive());
        assertTrue(board.getMatrix()[6][6].isCellActive());
        assertTrue(board.getMatrix()[8][5].isCellActive());

        //assert false for the tiles that should be active only if the number of player is 4
        assertTrue(board.getMatrix()[0][4].isCellActive());
        assertTrue(board.getMatrix()[1][5].isCellActive());
        assertTrue(board.getMatrix()[3][1].isCellActive());
        assertTrue(board.getMatrix()[4][0].isCellActive());
        assertTrue(board.getMatrix()[4][8].isCellActive());
        assertTrue(board.getMatrix()[5][7].isCellActive());
        assertTrue(board.getMatrix()[7][3].isCellActive());
        assertTrue(board.getMatrix()[8][4].isCellActive());
    }

    //testing that when I empty the board it actually removes the card associated with each tile for every type of board in terms of the number of players
    @Test
    void twoPlayersBoardIsEmpty(){
        board = new Board(2);
        //TODO: when I remove all the cards the board is empty
    }
    @Test
    void threePlayersBoardIsEmpty(){
        board = new Board(3);
        //TODO: when I remove all the cards the board is empty
    }
    @Test
    void fourPlayersBoardIsEmpty(){
        board = new Board(4);
        //TODO: when I remove all the cards the board is empty
    }

    //testing that when I call the method to fill the board it actually gets filled for each type of board in terms of the number of players
    @Test
    void twoPlayersBoardIsFull(){
        board = new Board(2);
        //TODO: when I fill the board it actually gets filled
    }
    @Test
    void threePlayersBoardIsFull(){
        board = new Board(3);
        //TODO: when I fill the board it actually gets filled
    }
    @Test
    void fourPlayersBoardIsFull(){
        board = new Board(4);
        //TODO: when I fill the board it actually gets filled
    }

}