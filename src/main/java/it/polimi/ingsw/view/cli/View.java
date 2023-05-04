package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.entities.Board;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.ResponseMessage;

/**
 * Standardization of all the methods that the Server uses to send requests, updates, error messages, etc
 * Implemented by VirtualView (called by GameController) and CLI/GUI (Server -> Client, CLI/GUI -> User)
 */
public interface View {

    //TODO: Creazione di un metodo per ogni oggetto specifico da inviare

    //Server requests Client: CLI/GUI requests User
    //region REQUESTS
    /**
     * The client interface asks the player his username
     */
    void requestUsername();

    /**
     * The client interface asks the player if he wants to create a new lobby and, if he doesn't,
     * the ID of the lobby he wants to join
     */
    void requestLobby();

    //TODO: Decidere se separare o meno da askLobby() la scelta del numero dei giocatori
    /**
     * The client interface asks the creator of the lobby the number of players that have to join
     * before starting the game
     */
    void requestNumberOfPlayers();

    /**
     * The client interface asks the player to select the cards
     */
    void requestCardSelection();

    /**
     * The client interface asks the player to choose the order of the cards to insert into his
     * bookshelf and the column where to insert them
     */
    void requestCardInsertion();

    //endregion

    //Server shows Client: CLI/GUI show User
    //region SHOW

    /**
     * Sends the coordinates of the cards successfully removed by the current player
     * @param coordinates coordinates of the removed cards
     */
    void showRemovedCards(int[][] coordinates);

    //TODO: Con che tipo di struttura dati viene fatta vedere l'informazione rilevante di Board alla CLI/GUI?
    /**
     * Sends the newly refilled board after the calling of the fillBoard() method
     * @param board refilled board
     */
    void showRefilledBoard(Board board);
    //endregion

    /**
     * Gives feedback to the client about his last command
     * @param response if command was successful/valid
     * @param responseType type of the command
     */
    void sendResponse(boolean response, MessageType responseType);

    /**
     * Gives a generic negative feedback to any type of command sent by the player that is not playing
     * the current turn
     */
    void sendNotYourTurn();
}
