package it.polimi.ingsw.view;

import java.util.ArrayList;

/**
 * Defines all the methods used by the user interface (CLI/GUI) to take input from keyboard and call the methods in
 * ClientController that generate the respective message to send to the Server
 * <p>
 * Contains the methods that the VirtualView doesn't need to implement
 */
public interface UserInterface extends View {

    /**
     * Gives feedback about the availability of the username sent to be checked
     * @param response if the username is available
     */
    void confirmUsername(boolean response);

    /**
     * Gives feedback about the creation of a lobby
     */
    void confirmCreation(String content);

    /**
     * Gives feedback about the joining of a lobby
     * @param response if the player successfully created/joined a lobby
     */
    void confirmAccess(boolean response, String content);

    /**
     * Displays the list of the usernames of the players connected to the lobby at that point in time
     * @param playerUsernames list of player usernames
     */
    void refreshConnectedPlayers(ArrayList<String> playerUsernames);

    /**
     * Gives feedback about the start of the game
     * @param response if the game started
     */
    void confirmStartGame(boolean response);

    void showChat(String chat);

}
