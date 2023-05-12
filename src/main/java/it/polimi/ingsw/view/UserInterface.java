package it.polimi.ingsw.view;

import java.util.ArrayList;

/**
 * Defines all the methods used by the user interface (CLI/GUI) to take input from keyboard and call the methods in
 * ClientController that generate the respective message to send to the Server
 * <p>
 * Contains the methods that the VirtualView doesn't need to implement
 */
public interface UserInterface extends View {

    void refreshConnectedPlayers(ArrayList<String> playeUsernames);

    void showSuccessfulConnection(int lobbyId);

    void showError(String content);


}
