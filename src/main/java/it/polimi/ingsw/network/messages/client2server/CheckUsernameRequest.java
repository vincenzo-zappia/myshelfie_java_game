package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Request sent by the player to check the availability of the username he chose
 */
public class CheckUsernameRequest extends Message {
    public CheckUsernameRequest(String username) {
        super("uninitialized", MessageType.USERNAME_REQUEST);
        setContent(username);
    }
}
