package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.messages.Message;

public class VirtualView implements Observer {

    private final ClientHandler clientHandler;

    public VirtualView(ClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public void update(Message message){
        clientHandler.sendMessage(message);
    }

    //TODO: Gestione particolare messaggi server -> client
}
