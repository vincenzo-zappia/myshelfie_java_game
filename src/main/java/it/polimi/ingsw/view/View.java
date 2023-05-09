package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.util.BoardCell;
import it.polimi.ingsw.util.Cell;

import java.util.ArrayList;

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
     * Gives feedback to the client about his last command
     * @param response if command was successful/valid
     * @param responseType type of the command
     */
    void sendResponse(boolean response, MessageType responseType);

    void sendInsertionResponse(Cell[][] bookshelf, boolean response);

    /**
     * Gives a generic negative feedback to any type of command sent by the player that is not playing
     * the current turn
     */
    void sendNotYourTurn();
}
