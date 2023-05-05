package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Classe che gestisce tutti i tipi di risposta che necessitano di un attributo booleano
 */
public class ResponseMessage extends Message {
    private final boolean response;
    public ResponseMessage(MessageType type, boolean response) {
        super("server", type);
        this.response = response;
    }

    public boolean getResponse() {
        return response;
    }
}
