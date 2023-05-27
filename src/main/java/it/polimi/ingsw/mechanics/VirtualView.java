package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Scoreboard;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import it.polimi.ingsw.view.View;

/**
 * Manages the creation of messages from server to client
 */
public class VirtualView implements View, Observer {

    //region ATTRIBUTES
    private final ClientHandler clientHandler;
    //endregion

    //region CONSTRUCTOR
    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }
    //endregion

    //region OBSERVER
    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }
    //endregion

    //region VIEW
    @Override
    public void sendGenericResponse(boolean response, String content) {
        clientHandler.sendMessage(new TextResponse(response, content));
    }

    @Override
    public void showCurrentPlayer(String currentPlayer) {
        clientHandler.sendMessage(new GenericMessage(currentPlayer, MessageType.CURRENT_PLAYER));
    }

    @Override
    public void sendCheckedCoordinates(boolean response, int[][] coordinates) {
        clientHandler.sendMessage(new CoordinatesMessage(response, coordinates, MessageType.CHECKED_COORDINATES));
    }

    @Override
    public void showRemovedCards(int[][] coordinates){
        clientHandler.sendMessage(new CoordinatesMessage(coordinates, MessageType.REMOVED_CARDS));
    }

    @Override
    public void showUpdatedBookshelf(Tile[][] bookshelf) {
        clientHandler.sendMessage(new BookshelfMessage(bookshelf));
    }

    @Override
    public void showRefilledBoard(BoardTile[][] boardTiles) {
        clientHandler.sendMessage(new BoardMessage(boardTiles));
    }

    @Override
    public void showCommonGoals(Goal[] commonGoals) {
        clientHandler.sendMessage(new CommonGoalsMessage(commonGoals));
    }

    @Override
    public void showPrivateGoal(PrivateGoal privateGoal) {
        clientHandler.sendMessage(new PrivateGoalMessage(privateGoal));
    }

    @Override
    public void showScoreboard(Scoreboard scoreboard){
        clientHandler.sendMessage(new ScoreboardMessage(scoreboard));
    }

    @Override
    public void showToken(String content) {
        clientHandler.sendMessage(new GenericMessage(content + " filled his bookshelf!", MessageType.TOKEN));
    }
    //endregion
}
