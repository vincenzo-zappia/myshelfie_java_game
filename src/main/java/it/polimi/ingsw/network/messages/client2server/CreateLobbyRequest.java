package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Request sent by the player to create a new lobby
 */
public class CreateLobbyRequest extends Message {

    public CreateLobbyRequest(String sender) {
        super(sender, MessageType.CREATE_LOBBY_REQUEST);
    }

}
