package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

public interface Observer {
    void update(Message message);
}
