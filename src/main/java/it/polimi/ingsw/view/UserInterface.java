package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

/**
 * Defines all the methods used by the user interface (CLI/GUI) to take input from keyboard and call the methods in
 * ClientController that generate the respective message to send to the Server
 *
 * Contains the methods that the VirtualView doesn't need to implement
 */
public interface UserInterface extends View {

    void refreshConnectedPlayers(ArrayList<String> playeUsernames);

    void showSuccessfulConnection(int lobbyId);

    void showConfirmation(MessageType type);

    void showError(String content);

    /**
     * The client interface asks the player to select the cards
     */
    void requestCardSelection();

    /**
     * The client interface asks the player to choose the order of the cards to insert into his
     * bookshelf and the column where to insert them
     */
    void requestCardInsertion();

}
