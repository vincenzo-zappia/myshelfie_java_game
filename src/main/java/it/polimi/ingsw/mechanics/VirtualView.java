package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;

public class VirtualView implements Observer {

    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

    //TODO: Gestione particolare messaggi server -> client

    /**
     * Method that after each turn sends the updated Board (without the Cards corresponding to the
     * coordinates
     * @param coordinates coordinates of the removed cards
     */
    public void sendCardRemoval(int[][] coordinates){
        clientHandler.sendMessage(new CardRemovalMessage(coordinates));
    }

    /**
     * Method that after each refill sends the new Board to the clients
     * @param board refilled Board
     */
    public void sendBoardRefill(Board board){
        clientHandler.sendMessage(new BoardRefillUpdate(board));
    }

    /**
     * Method that gives feedback to the player about his last sent command.
     * @param response boolean that is true if the command sent by the client was accepted, 0 otherwise
     */
    public void sendResponse(boolean response){
        clientHandler.sendMessage(new ResponseMessage(response));
    }
}
