package it.polimi.ingsw.view;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;

import java.util.HashMap;

/**
 * Standardization of all the methods that the Server uses to send requests, updates, error messages, etc
 * Implemented by VirtualView (called by GameController) and CLI/GUI (Server -> Client, CLI/GUI -> User)
 */
public interface View {

    //TODO: Creazione di un metodo per ogni oggetto specifico da inviare

    /**
     * Sends the coordinates of the cards successfully removed by the current player
     * @param coordinates coordinates of the removed cards
     */
    void showRemovedCards(int[][] coordinates);

    //TODO: Con che tipo di struttura dati viene fatta vedere l'informazione rilevante di Board alla CLI/GUI?
    /**
     * Sends the newly refilled board after the calling of the fillBoard() method
     * @param boardCells refilled board
     */
    void showRefilledBoard(BoardCell[][] boardCells);

    /**
     * Shows the username of the current player
     * @param currentPlayer username of the current player
     */
    void showCurrentPlayer(String currentPlayer);

    /**
     * Updates all the players with the scoreboard at the end of the game
     * @param scoreboard endgame scoreboard ordered by points
     */
    void showScoreboard(HashMap<String, Integer> scoreboard);

    /**
     * Gives feedback to the client about his last command
     * @param response if command was successful/valid
     * @param responseType type of the command
     */
    void sendResponse(boolean response, MessageType responseType, String content);

    /**
     * Gives feedback to the player about his insertion command
     * @param bookshelf updated bookshelf in case the insertion was successful, no changes otherwise
     * @param response if the insertion was successful
     */
    void sendInsertionResponse(Cell[][] bookshelf, boolean response);

    /**
     * Gives a generic negative feedback to any type of command sent by the player that is not playing
     * the current turn
     */
    void sendNotYourTurn(String content);

    /**
     * Sends to a player his private goal and the common goals of the game
     * @param commonGoals common goals of the game
     * @param privateGoal player-specific goal
     */
    void sendGoals(Goal[] commonGoals, PrivateGoal privateGoal);

}
