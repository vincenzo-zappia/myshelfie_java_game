package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.server2client.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;
import it.polimi.ingsw.view.View;

/**
 * Class that manages the creation of messages server -> client. Used by Lobby, GameController
 */
public class VirtualView implements View, Observer {
    private final ClientHandler clientHandler;
    private int[][] coordinates; //TODO: Toglierlo e metterlo in GameController in variabile locale
    private boolean updated = false; //TODO: Toglierlo e metterlo in GameController in variabile locale

    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

    @Override
    public void showRemovedCards(int[][] coordinates){
        this.coordinates = coordinates;
        updated = true;
    }

    public void sendRemovalMessage(){
        if(updated) clientHandler.sendMessage(new CardRemovalMessage(coordinates));
        updated = false;
    }

    @Override
    public void showRefilledBoard(BoardCell[][] boardCells) {
        clientHandler.sendMessage(new BoardRefillUpdate(boardCells));
    }

    //region STRING
    @Override
    public void showCurrentPlayer(String currentPlayer) {
        clientHandler.sendMessage(new GenericMessage(MessageType.CURRENT_PLAYER, currentPlayer));
    }
    //endregion

    //region BOOLEAN
    @Override
    public void sendResponse(boolean response, MessageType responseType) {
        clientHandler.sendMessage(new ResponseMessage(responseType, response));
    }
    //endregion

    @Override
    public void sendInsertionResponse(Cell[][] bookshelf, boolean response) {
        clientHandler.sendMessage(new InsertionResponseMessage(bookshelf, response));
    }

    @Override
    public void sendNotYourTurn() {
        clientHandler.sendMessage(new ResponseMessage(MessageType.NOT_YOUR_TURN, false));
    }
}
