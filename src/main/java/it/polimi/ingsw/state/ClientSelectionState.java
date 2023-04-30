package it.polimi.ingsw.state;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.ResponseMessage;

public class ClientSelectionState implements TurnState {
    private final Client client;

    public ClientSelectionState(Client client){
        this.client = client;
    }

    @Override
    public void messageHandler(Message message) {
        switch (message.getType()){
            case SELECTION_RESPONSE -> {
                ResponseMessage response = (ResponseMessage) message;
                if(response.getResponse()) return;
                //TODO: Abilitazione tasti insertion, modifica ordine e colonna in GUI
                //TODO: Creazione messaggio invio effettivo cartein ordine e colonna selezionata

                //Aggiornamento stato del client
                client.setClientState(new ClientInsertionState(client));
            }
            case INSERTION_RESPONSE -> {
                //TODO: Gestione errore
            }
            case BOARD_REFILL -> {}
        }
    }
}
