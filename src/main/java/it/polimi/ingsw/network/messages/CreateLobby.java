package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.entities.Player;

//TODO: eliminare;
public class CreateLobby extends Message {

    //region ATTRIBUTES
    private final Player creator;
    //endregion

    protected CreateLobby(String sender, MessageType type, Player creator) {
        super(sender, type);
        this.creator = creator;
    }


    public Player getCreator() {
        return creator;
    }
}
