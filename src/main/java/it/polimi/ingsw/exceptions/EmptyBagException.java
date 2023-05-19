package it.polimi.ingsw.exceptions;

public class EmptyBagException extends RuntimeException{
    public EmptyBagException(){
        super("The card bag is empty!");
    }
}
