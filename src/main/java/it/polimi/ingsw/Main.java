package it.polimi.ingsw;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.AddCardException;
import it.polimi.ingsw.util.CardType;

public class Main {
    public static void main(String[] args) {

        Bookshelf b = new Bookshelf();

        for(int i=0; i<=6; i++){
            try {
                b.addCard(0, new Card("books1.png", CardType.BOOKS));
            } catch (AddCardException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
