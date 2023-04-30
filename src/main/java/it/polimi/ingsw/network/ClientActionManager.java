package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.state.TurnState;

public class ClientActionManager implements Observer {
    private TurnState clientState;

    public ClientActionManager(){
        //TODO: Decidere come gestire fase iniziale se da costruttore o dal primo messaggio inviato da server
    }

    /**
     * Manages the sending of messages from client to server relatively to the turn of the client
     * @param message
     */
    @Override
    public void update(Message message){
        clientState.messageHandler(message);
    }
}
