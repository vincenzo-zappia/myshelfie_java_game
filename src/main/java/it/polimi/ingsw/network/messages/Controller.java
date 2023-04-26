package it.polimi.ingsw.network.messages;

//TODO: per serialization/deserialization si può usare la libreria Jackson.
// link utile: https://www.baeldung.com/jackson-xml-serialization-and-deserialization

public class Controller {
    /*public synchronized void handleMessage(Message m){
        m.visit(this);
    }*/

    public synchronized void handle(NicknameMessage m){
        //TODO implementazione con libreria Jackson
    }

    public synchronized void handle(PlayerMessage m){
        //TODO implementazione con libreria Jackson
    }

    public synchronized void handle(GameUpdateMessage m){
        //TODO implementazione con libreria Jackson
    }

    public synchronized void handle(MoveMessage m){
        //TODO implementazione con libreria Jackson
    }
}
