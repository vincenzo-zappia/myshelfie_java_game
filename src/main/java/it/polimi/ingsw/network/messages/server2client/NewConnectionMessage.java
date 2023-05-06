package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

public class NewConnectionMessage extends Message {
    private final ArrayList<String> usernameList;
    public NewConnectionMessage(ArrayList<String> usernameList) {
        super("server", MessageType.NEW_CONNECTION);
        this.usernameList = usernameList;
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }
}
