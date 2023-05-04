package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

public interface Observer {
    public void update(Message message);
}
