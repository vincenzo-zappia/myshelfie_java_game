package it.polimi.ingsw.network.messages.client2server;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Request made by the lobby master to start the game
 */
public class StartGameRequest extends Message {

    public StartGameRequest(String sender) {
        super(sender, MessageType.START_GAME_REQUEST);
    }
    
}
