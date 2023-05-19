package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.util.BoardTile;
import it.polimi.ingsw.util.Tile;
import it.polimi.ingsw.view.View;

import java.util.HashMap;

/**
 * Class that manages the creation of messages from server to client. Used by Lobby, GameController
 */
public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

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
    public void sendCheckedCoordinates(int[][] coordinates) {
        clientHandler.sendMessage(new CoordinatesMessage(coordinates, MessageType.CHECKED_COORDINATES));
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
    public void showGoalsDetails(Goal[] commonGoals, PrivateGoal privateGoal) {
        clientHandler.sendMessage(new GoalsMessage(commonGoals, privateGoal));
    }

    @Override
    public void showScoreboard(HashMap<String, Integer> scoreboard){
        clientHandler.sendMessage(new ScoreboardMessage(scoreboard));
    }
    //endregion

}
