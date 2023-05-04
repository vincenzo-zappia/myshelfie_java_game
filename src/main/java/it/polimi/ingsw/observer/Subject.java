package it.polimi.ingsw.observer;

import java.util.ArrayList;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

//Subject maintains a list of its observers and notifies them of any state changes
public abstract class Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * adds observer to the list
     * @param o observer to add
     */
    public void register(Observer o) {
        observers.add(o);
    }

    /**
     * removes observer from the list
     */
    public void unregister(Observer o) {
        observers.remove(o);
    }

    //TODO: Capire che parametro far prendere per far passarevalori impacchetto messaggio CLI -> ClientActionManager
    /**
     * notifies all observers in the list
     * @param message object with the updated information
     */
    public void notifyObserver(Message message) {
        for(Observer o : observers){
            o.update(message);
        }
    }

}
