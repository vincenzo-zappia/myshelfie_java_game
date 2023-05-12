package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Classe che gestisce tutti i tipi di risposta che necessitano di un attributo booleano
 */
public class BooleanResponse extends Message {
    private final boolean response;
    public BooleanResponse(MessageType type, boolean response, String content) {
        super("server", type);
        this.response = response;
        setContent(content);
    }

    public boolean getResponse() {
        return response;
    }
}
