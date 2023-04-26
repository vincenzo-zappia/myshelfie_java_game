package it.polimi.ingsw.mechanics;

import it.polimi.ingsw.network.ClientHandler;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.network.messages.Message;

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
