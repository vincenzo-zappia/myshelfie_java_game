package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.network.messages.*;
import it.polimi.ingsw.observer.Observer;

/**
 * Class that manages the creation of messages server -> client
 */
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
     * Sends the coordinates of the cards successfully removed by the current player
     * @param coordinates coordinates of the removed cards
     */
    public void sendCardRemoval(int[][] coordinates){
        clientHandler.sendMessage(new CardRemovalMessage(coordinates));
    }

    /**
     * Sends the new refilled Board
     * @param board refilled Board
     */
    public void sendBoardRefill(Board board){
        clientHandler.sendMessage(new BoardRefillUpdate(board));
    }

    //region RESPONSE
    //All the methods that use the generic boolean ResponseMessage

    /**
     * Gives feedback to the client about his selection command
     * @param response if selection is valid
     */
    public void sendSelectionResponse(boolean response){
        clientHandler.sendMessage(new ResponseMessage(MessageType.SELECTION_RESPONSE, response));
    }

    /**
     * Gives feedback to the client about his insertion command
     * @param response if insertion is valid
     */
    public void sendInsertionResponse(boolean response){
        clientHandler.sendMessage(new ResponseMessage(MessageType.INSERTION_RESPONSE, response));
    }

    /**
     * Gives a generic negative feedback to any type of command sent by the player that is not playing
     * the current turn
     */
    public void sendNotYourTurn(){
        clientHandler.sendMessage(new ResponseMessage(MessageType.NOT_YOUR_TURN, false));
    }
    //endregion
}
