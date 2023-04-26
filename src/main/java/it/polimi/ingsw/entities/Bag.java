/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 25/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: users draw card from bag, this class need to be improved.
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.exceptions.NoMoreCardsException;
import java.util.ArrayList;
import java.util.Collections;

public class Bag {
    //METHOD 2: ARRAY OF ALL POSSIBLE CARDS

    //region ATTRIBUTES
    private final ArrayList<Card> bag;

    //TODO: how to manage colors and imasge. Now thought about as String and integer arrays
    private final String[][] cardImgName = {
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

        //for each one of the 6 colors create 22 cards
        for (int i = 0; i < 6; i++)
            for(int j = 0; j < 22; j++)
                bag.add(new Card(cardImgName[i][j%3], TileType.values()[i]));

        //method that shuffles the bag
        Collections.shuffle(bag);
    }
    //endregion

    //region METHODS
    public boolean isBagEmpty(){
        return bag.size() == 0;
    }

    public Card drawCard() throws NoMoreCardsException{
            return bag.remove(bag.size() - 1);
    }
    //endregion

//region OLD CODE

    //METHOD 1: KEEPING TRACK ONLY OF COLORS
    //problem: how to keep track of the images already used
    /*
    //region Attributes
    private int[] types;
    private int numUsedCard; //keeps track of the number of used cards in one game
    private static final int MAX_CARD_NUM = 132;
    private static final int MAX_CARD_FOR_TYPE = 22;

//    private Card cards[];
//    private int actualIndex;
    //endregion

    public Bag(){
        types = new int[]{0, 0, 0, 0, 0, 0};
        numUsedCard = 0;
//        cards = new Card[CARD_NUM];
//        actualIndex=132;
    }


    //region Methods
    //returns true if the bag is empty, meaning that all the 132 cards have been used in a game
    //one of the conditions by which the game could end
    public boolean isEmpty(){
        return (numUsedCard == MAX_CARD_NUM);
    }

    public Card drawACard(){
        if(isEmpty()){
            //TODO: generare eccezione?
            return null;
        }

        Card card = new Card();
        while(types[card.getColor()]>=MAX_CARD_FOR_TYPE){
            card = new Card();
        }

        types[card.getColor()]++;
        numUsedCard++;

        return card;
    }
    */

    //endregion

}
