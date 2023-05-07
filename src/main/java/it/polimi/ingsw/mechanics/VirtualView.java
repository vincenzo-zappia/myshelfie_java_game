package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.network.messages.server2client.BoardRefillUpdate;
import it.polimi.ingsw.network.messages.server2client.CardRemovalMessage;
import it.polimi.ingsw.network.messages.server2client.CurrentPlayerMessage;
import it.polimi.ingsw.network.messages.server2client.ResponseMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;

//TODO: Aggiornare descrizione nel caso in cui TurnManager diventi TurnController
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
        clientHandler.sendMessage(new CardRemovalMessage(coordinates));
    }

    @Override
    public void showRefilledBoard(BoardCell[][] boardCells){
        clientHandler.sendMessage(new BoardRefillUpdate(boardCells));
    }

    @Override
    public void showCurrentPlayer(String currentPlayer){
        clientHandler.sendMessage(new CurrentPlayerMessage(currentPlayer));
    }

    @Override
    public void sendResponse(boolean response, MessageType responseType){
        clientHandler.sendMessage(new ResponseMessage(responseType, response));
    }

    @Override
    public void sendNotYourTurn(){
        clientHandler.sendMessage(new ResponseMessage(MessageType.NOT_YOUR_TURN, false));
    }
}
