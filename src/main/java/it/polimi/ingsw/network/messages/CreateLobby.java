package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Player;

//TODO: eliminare;
public class CreateLobby extends Message {

    public CreateLobby(String sender) {
        super(sender, MessageType.CREATE_LOBBY);
    }
}
