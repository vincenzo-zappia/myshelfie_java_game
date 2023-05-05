package it.polimi.ingsw.state;

import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.server2client.ResponseMessage;

public class ClientInsertionState implements TurnState {
    private final ClientController client;

    public ClientInsertionState(ClientController client){
        this.client = client;
    }

    @Override
    public void messageHandler(Message message) {
        switch (message.getType()) {
            case SELECTION_RESPONSE -> {
                //TODO: Gestion errore
            }
            case INSERTION_RESPONSE -> {
                ResponseMessage response = (ResponseMessage) message;
                if(response.getResponse()) return;
                //TODO: Aggiorna GUI (fine turno)

                //Aggiornamento stato del client
                client.setTurnState(new ClientSelectionState(client));
            }
        }
    }
}