package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

public class NewConnectionUpdate extends Message {
    private final ArrayList<String> usernameList;
    public NewConnectionUpdate(ArrayList<String> usernameList) {
        super("server", MessageType.NEW_CONNECTION_UPDATE);
        this.usernameList = usernameList;
    }

    public ArrayList<String> getUsernameList() {
        return usernameList;
    }
}
