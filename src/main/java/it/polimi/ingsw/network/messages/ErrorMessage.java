package it.polimi.ingsw.network.messages;

/**
 * Generic message on par with ResponseMessage used to pass specific error content
 */
public class ErrorMessage extends Message{

    public ErrorMessage(String description) {
        super("server", MessageType.ERROR_MESSAGE);
        this.setContent("ERROR: " + description);
    }

}
