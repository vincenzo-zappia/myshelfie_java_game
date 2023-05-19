package it.polimi.ingsw.exceptions;

public class FullColumnException extends RuntimeException{
    public FullColumnException(){
        super("The column is full!");
    }
}
