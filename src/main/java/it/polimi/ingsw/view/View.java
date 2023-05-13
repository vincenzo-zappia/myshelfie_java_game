package it.polimi.ingsw.view;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;

import java.util.HashMap;

/**
 * Standardization of all the methods that the Server uses to send requests, updates, error messages, etc
 * Implemented by VirtualView (called by GameController) and CLI/GUI (Server -> Client, CLI/GUI -> User)
 */
public interface View {

    /**
     * Gives feedback to the client about his last command
     * @param response if command was successful/valid (needed by CLI/GUI to differentiate between positive and
     *                 negative feedback
     */
    void sendGenericResponse(boolean response, String content);

    /**
     * Shows the username of the current player
     * @param currentPlayer username of the current player
     */
    void showCurrentPlayer(String currentPlayer);

    /**
     * Sends back the selected coordinates if the selection is valid
     * @param coordinates coordinates selected by the player
     */
    void sendCheckedCoordinates(int[][] coordinates);

    /**
     * Sends the coordinates of the cards successfully removed by the current player
     * @param coordinates coordinates of the removed cards
     */
    void showRemovedCards(int[][] coordinates);

    /**
     * Sends the updated bookshelf to the player after the insertion
     * @param bookshelf updated bookshelf
     */
    void showUpdatedBookshelf(Cell[][] bookshelf);

    /**
     * Sends the newly refilled board after the calling of the fillBoard() method
     * @param boardCells refilled board
     */
    void showRefilledBoard(BoardCell[][] boardCells);

    /**
     * Sends to a player his private goal and the common goals of the game
     * @param commonGoals common goals of the game
     * @param privateGoal player-specific goal
     */
    void showGoalsDetails(Goal[] commonGoals, PrivateGoal privateGoal);

    /**
     * Updates all the players with the scoreboard at the end of the game
     * @param scoreboard endgame scoreboard ordered by points
     */
    void showScoreboard(HashMap<String, Integer> scoreboard);

}
