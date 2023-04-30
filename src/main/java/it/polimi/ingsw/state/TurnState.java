package it.polimi.ingsw.state;

import it.polimi.ingsw.network.messages.Message;

//TODO: Se avanza tempo implementarla nel GameController
public interface TurnState {
    public void messageHandler(Message message);
}
