package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * serve ad ordinare di creare una lobby da parte del server
 */
public class CreateLobbyMessage extends Message {

    public CreateLobbyMessage(String sender) {
        super(sender, MessageType.CREATE_LOBBY);
    }
}
