package it.polimi.ingsw.network.messages.server2client;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

/**
 * Classe che gestisce tutti i tipi di risposta che necessitano di un attributo booleano
 */
public class GenericResponse extends Message {
    private final boolean response;
    public GenericResponse(boolean response, String content) {
        super("server", MessageType.GENERIC_RESPONSE);
        this.response = response;
        setContent(content);
    }

    public boolean getResponse() {
        return response;
    }
}
