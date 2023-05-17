package it.polimi.ingsw.view;

import java.util.ArrayList;

//TODO: Vedere se si riesce a rimuovere (ora serve a standardizzare l'invio di messaggi che arrivano da Lobby: livello superiore a GameController)
/**
 * Defines all the methods used by the user interface (CLI/GUI) to take input from keyboard and call the methods in
 * ClientController that generate the respective message to send to the Server
 * <p>
 * Contains the methods that the VirtualView doesn't need to implement
 */
public interface UserInterface extends View {

    /**
     * Displays the list of the usernames of the players connected to the lobby at that point in time
     * @param playerUsernames list of player usernames
     */
    void refreshConnectedPlayers(ArrayList<String> playerUsernames);

    /**
     * Checks the flag that allows the user interface to start taking game commands from the player
     */
    void showAccessResponse(boolean response, String content);
}
