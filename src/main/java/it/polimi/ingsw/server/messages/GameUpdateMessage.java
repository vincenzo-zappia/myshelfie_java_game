package it.polimi.ingsw.server.messages;

public class GameUpdateMessage implements Message{

    //NOTE: usato per inviare intero game (istanza)
    // modificare nome se non si aggiornerà più gioco in questo modo, poichè la classe è
    // utilizzata solamente per implementare pattern visitor.

    @Override
    public void visit(Controller c) {
        c.handle(this);
    }
}
