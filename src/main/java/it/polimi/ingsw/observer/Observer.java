package it.polimi.ingsw.observer;

import it.polimi.ingsw.server.messages.Message;

public interface Observer {
    public void update(Message message);
}
