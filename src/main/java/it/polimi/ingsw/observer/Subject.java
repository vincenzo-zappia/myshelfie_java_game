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

    /**
     * Overload of method notifyObserver() in order to allow the CLI to update its observer by passing
     * the keyboard input of the user as parameter
     * @param userInput string user keyboard input
     * @param type message type useful to ClientActionManager to creare a Message object out of the user
     *             input
     */
    public void notifyObserver(String userInput, MessageType type){
        for(Observer o : observers){
            //TODO: Capire come e dove creare messaggio utente CLI -> Manager -> Server
        }
    }
}
