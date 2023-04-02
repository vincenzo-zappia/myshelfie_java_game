package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.ServerSideController;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.state.Game;

import java.util.ArrayList;

public class GameController {
    private final ArrayList<String> playerUsernames;
    private boolean endGame;
    private final Game game;
    private final ServerSideController serverSideController;
    private String currentPlayer;

    public GameController(ArrayList<String> playerUsernames, Game game, ServerSideController serverSideController){
        this.playerUsernames = playerUsernames;
        currentPlayer = playerUsernames.get(0); //TODO: the couch
        this.game = game;
        this.serverSideController = serverSideController;
        endGame = false;

    }

    //method that sets the current player
    public void nextTurn(){
        int currentPlayerIndex = playerUsernames.indexOf(currentPlayer);

        //circular iteration of turns
        if(currentPlayerIndex + 1 < playerUsernames.size()) currentPlayerIndex += 1;
        else currentPlayerIndex = 0;
    }

    //method that checks the validity of the player's move and calls the game methods
    //TODO: review required
    public void turnAction() throws AddCardException {
        //int playerIndex = playerUsernames.indexOf(currentPlayer);

        //TODO: verification of move legality

        //first xml delivers the coordinate of the cards to be removed
        //TODO: XML extraction
        int[][] coordinates = new int[][]{{2, 3}};

        //Removal of the selected cards form the game board
        game.removeCardFromBoard(coordinates);

        //second xml delivers the array of cards to insert into the player's bookshelf in order and the respective column
        //TODO: XML extraction
        Card[] selectedCards = new Card[3];
        int selectedColumn = 0;

        //Insertion of the cards removed from the board into the player's bookshelf
        game.addCardToBookshelf(currentPlayer, selectedColumn, selectedCards);

        //TODO: logic for the triggers for the ending of the game
        if(endGame) findWinner();
    }

    //method that creates the final scoreboard
    public void findWinner(){
        game.scoreCommonGoal();
        game.scorePrivateGoal();

        //TODO: creation of the scoreboard based on the calculated scores for each one of the players
    }

}
