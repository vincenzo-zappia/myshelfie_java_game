package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

/**
 * Message containing the updated username list of the players in a lobby
 */
public class UsernameListMessage extends Message {
    private final ArrayList<String> usernameList;

    public UsernameListMessage(ArrayList<String> usernameList) {
        super("server", MessageType.NEW_CONNECTION);
        this.usernameList = usernameList;
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }

}
