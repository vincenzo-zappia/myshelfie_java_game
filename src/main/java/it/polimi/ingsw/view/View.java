package it.polimi.ingsw.view;

import it.polimi.ingsw.entities.Scoreboard;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;

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
     * Sends back the selected coordinates and gives feedback about its validity
     * @param valid if the selection is valid
     * @param coordinates coordinates selected by the player
     */
    void sendCheckedCoordinates(boolean valid, int[][] coordinates);

    /**
     * Sends the coordinates of the cards successfully removed by the current player
     * @param coordinates coordinates of the removed cards
     */
    void showRemovedCards(int[][] coordinates);

    /**
     * Sends the updated bookshelf to the player after the insertion
     * @param bookshelf updated bookshelf
     */
    void showUpdatedBookshelf(Tile[][] bookshelf);

    /**
     * Sends the newly refilled board after the calling of the fillBoard() method
     * @param boardTiles refilled board
     */
    void showRefilledBoard(BoardTile[][] boardTiles);

    /**
     * Sends the game common goals details to the player
     * @param commonGoals common goals of the game
     */
    void showCommonGoals(Goal[] commonGoals);

    /**
     * Sends to the player the details of his specific private goal
     * @param privateGoal player-specific private goal
     */
    void showPrivateGoal(PrivateGoal privateGoal);

    //TODO: Nfunziona fratm
    /**
     * Updates all the players with the scoreboard at the end of the game
     * @param scoreboard endgame scoreboard ordered by points
     */
    void showScoreboard(Scoreboard scoreboard);

    /**
     * Allows for the bonus point token to be taken by the first player who filled his bookshelf
     * @param content message containing the username of the player
     */
    void showToken(String content);

}
