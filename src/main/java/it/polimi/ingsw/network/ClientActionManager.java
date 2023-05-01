package it.polimi.ingsw.network;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.state.ClientSelectionState;
import it.polimi.ingsw.state.TurnState;

public class ClientActionManager implements Observer {
    private TurnState turnState;

    public ClientActionManager(){
        //TODO: Decidere come gestire fase iniziale se da costruttore o dal primo messaggio inviato da server
        turnState = new ClientSelectionState(this);
    }

    //TODO: Decidere se sarà ClientActionManager o CLI ad impacchettare i Messaggi dagli input del player
    /**
     * Manages the sending of messages from client to server relatively to the turn of the client
     * @param message message received from the client interface
     */
    @Override
    public void update(Message message){
        turnState.messageHandler(message);
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }
}
