package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;
import it.polimi.ingsw.view.View;

import java.util.HashMap;

/**
 * Class that manages the creation of messages server -> client. Used by Lobby, GameController
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

    @Override
    public void showRemovedCards(int[][] coordinates){
        clientHandler.sendMessage(new CardsRemoveUpdate(coordinates));
    }

    @Override
    public void showRefilledBoard(BoardCell[][] boardCells) {
        clientHandler.sendMessage(new BoardRefillUpdate(boardCells));
    }

    @Override
    public void showCurrentPlayer(String currentPlayer) {
        clientHandler.sendMessage(new GenericMessage(MessageType.CURRENT_PLAYER_UPDATE, currentPlayer));
    }

    @Override
    public void showScoreboard(HashMap<String, Integer> scoreboard){
        clientHandler.sendMessage(new ScoreboardMessage(scoreboard));
    }
    @Override
    public void sendResponse(boolean response, MessageType responseType, String content) {
        clientHandler.sendMessage(new ResponseMessage(responseType, response, content));
    }

    @Override
    public void sendInsertionResponse(Cell[][] bookshelf, boolean response) {
        clientHandler.sendMessage(new InsertionResponse(bookshelf, response));
    }

    @Override
    public void sendNotYourTurn(String content) {
        clientHandler.sendMessage(new ResponseMessage(MessageType.NOT_YOUR_TURN, false, content));
    }

    @Override
    public void sendGoals(Goal[] commonGoals, PrivateGoal privateGoal) {
        clientHandler.sendMessage(new GoalsMessage(commonGoals, privateGoal));
    }

}
