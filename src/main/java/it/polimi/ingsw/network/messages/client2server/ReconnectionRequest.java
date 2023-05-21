package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Message Class that client send to server when trying to reconnect
 */
public class ReconnectionRequest extends Message {

    private int lobby_id;

    protected ReconnectionRequest(String sender, int lobby_id) {
        super(sender, MessageType.RECONNECTION_REQUEST);
    }
}
