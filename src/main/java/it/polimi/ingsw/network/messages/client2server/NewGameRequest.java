package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public class NewGameRequest extends Message {

    private final boolean newGame;

    public NewGameRequest(String sender, boolean newGame) {
        super(sender, MessageType.NEW_GAME_REQUEST);
        this.newGame = newGame;
    }

    public boolean getNewGame() {
        return newGame;
    }

}
