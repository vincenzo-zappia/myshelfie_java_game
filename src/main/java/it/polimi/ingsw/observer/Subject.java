package it.polimi.ingsw.observer;

import java.util.ArrayList;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.messages.Message;

//Subject maintains a list of its observers and notifies them of any state changes
public abstract class Subject {
    private ArrayList<Observer> observers = new ArrayList<>();

    //adds observer to the list
    public void register(Observer o) {
        observers.add(o);
    }

    //removes observer from the list
    public void unregister(Observer o) {
        observers.remove(o);
    }

    //notifies all observers in the list
    public void notifyObserver(Message message) {
        for(Observer o : observers){
            o.update(message);
        }
    }
}
