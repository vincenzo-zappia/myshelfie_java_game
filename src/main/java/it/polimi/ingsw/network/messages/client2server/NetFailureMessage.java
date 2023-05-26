package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message Class that client send to server when trying to reconnect
 */
public class NetFailureMessage extends Message {
    public NetFailureMessage(String sender) {
        super(sender, MessageType.FORCE_DISCONNECTION);
    }
}
