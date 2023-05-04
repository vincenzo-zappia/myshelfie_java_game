package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.ArrayList;

/**
 * Same as Subject but differs from it in the declaration of the method notifyObservers(). Extended by CLI/GUI.
 * Both have to be able to pass different types of parameters to ClientController so that it can create specific messages
 */
public class SubjectView {
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

    /**
     * Notifies all the observers in the list
     * @param userInput string user keyboard input
     * @param type message type useful to ClientController to create Message objects out of user string inputs
     */
    public void notifyObserver(String userInput, MessageType type){
        for(Observer o : observers){
            //TODO: Capire come e dove creare messaggio utente CLI -> Manager -> Server
        }
    }
}
