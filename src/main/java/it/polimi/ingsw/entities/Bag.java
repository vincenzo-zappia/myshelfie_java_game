/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 25/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: users draw card from bag, this class need to be improved.
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.exceptions.EmptyBagException;
import it.polimi.ingsw.entities.util.CardType;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Container with all the cards of the game
 */
public class Bag {

    //region ATTRIBUTES
    private final ArrayList<Card> bag;
    private static final String[][] cardImgName = {
            {"frames1.png", "frames2.png", "frames3.png"},
            {"cats1.png", "cats2.png", "cats3.png"},
            {"games1.png", "games2.png", "games3.png"},
            {"books1.png", "books2.png", "books3.png"},
            {"plants1.png", "plants2.png", "plants3.png"},
            {"trophies1.png", "trophies2.png", "trophies3.png"}
    };
    //endregion

    //region CONSTRUCTOR
    public Bag() {
        bag = new ArrayList<>();

        //Creating 22 cards for each one of the 6 colors
        for (int i = 0; i < 6; i++)
            for(int j = 0; j < 22; j++)
                bag.add(new Card(cardImgName[i][j%3], CardType.values()[i]));

        //Shuffling the bag so that the order of extraction is random
        Collections.shuffle(bag);
    }
    //endregion

    //region METHODS
    /**
     * Draws a random card from the bag
     * @return the random card
     * @throws EmptyBagException if there are no more cards in the bag
     */
    public Card drawCard() throws EmptyBagException {
        if (isBagEmpty()) throw new EmptyBagException();
        return bag.remove(bag.size() - 1);
    }

    /**
     * Checks if the bag is empty
     * @return if the bag is empty
     */
    public boolean isBagEmpty(){
        return bag.size() == 0;
    }
    //endregion

}
