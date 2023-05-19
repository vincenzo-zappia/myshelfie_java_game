package it.polimi.ingsw.exceptions;

public class GetCardException extends RuntimeException{
    public GetCardException(){
        super("The tile has no card in it!");
    }
}
