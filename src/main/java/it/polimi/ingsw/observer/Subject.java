package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

/**
 * A subject maintains a list of its observers and notifies them of any state changes
 */
public interface Subject {
    ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Adds observer to the list
     * @param o observer to add
     */
    void register(Observer o);

    /**
     * Removes observer from the list
     */
    void unregister(Observer o);

    /**
     * Notifies all observers in the list
     * @param message object with the updated information
     */
    void notifyObserver(Message message);

}
