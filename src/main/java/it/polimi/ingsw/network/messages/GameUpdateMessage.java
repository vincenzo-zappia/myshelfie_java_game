package it.polimi.ingsw.network.messages;

public class GameUpdateMessage extends Message{
    protected GameUpdateMessage(String sender, MessageType type) {
        super(sender, type);
    }

    //NOTE: usato per inviare intero game (istanza)
    // modificare nome se non si aggiornerà più gioco in questo modo, poichè la classe è
    // utilizzata solamente per implementare pattern visitor.

    /*
    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
    */
}
