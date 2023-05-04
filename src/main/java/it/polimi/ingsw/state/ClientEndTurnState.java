package it.polimi.ingsw.state;

import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.network.messages.Message;

public class ClientEndTurnState implements TurnState {
    private final ClientController client;
    public ClientEndTurnState(ClientController client){
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
