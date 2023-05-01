package it.polimi.ingsw.state;

import it.polimi.ingsw.network.ClientActionManager;
import it.polimi.ingsw.network.messages.Message;

public class ClientEndTurnState implements TurnState {
    private final ClientActionManager client;
    public ClientEndTurnState(ClientActionManager client){
        this.client = client;
    }
    @Override
    public void messageHandler(Message message) {
        switch (message.getType()){
            case BOARD_REFILL -> {}
            case SELECTION_RESPONSE -> {}
            case INSERTION_RESPONSE -> {}
        }
    }
}
