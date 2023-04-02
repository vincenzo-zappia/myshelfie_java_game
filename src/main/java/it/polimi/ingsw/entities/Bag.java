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

    //REGION ATTRIBUTES
    private ArrayList<Card> bag;

    //TODO: how to manage colors and images. Now thought about as String and integer arrays
    private final String[][] cardImgName = {
            {"blue1.png", "blue2.png", "blue3.png"},
            {"green1.png", "green2.png", "green3.png"},
            {"orange1.png", "orange2.png", "orange3.png"},
            {"white1.png", "white2.png", "white3.png"},
            {"pink1.png", "pink2.png", "pink3.png"},
            {"lBlue1.png", "lBlue2.png", "lBlue3.png"}
    };
    private final int cardColor[] = {1, 2, 3, 4, 5, 6}; //TODO: might be implemented with an enumeration
    //END REGION

    //REGION CONSTRUCTOR
    public Bag() {
        bag = new ArrayList<>();

        //loop for each one of the 6 colors
        for (int i = 0; i < cardColor.length; i++) {
            //for each color there are 3 types of images
            for (int j = 0; j < 3; j++) {
                //creation of 7 copies of the same card
                for (int k = 0; k < 7; k++) {
                    bag.add(new Card(cardImgName[i][j], cardColor[i]));

                    //TODO: how to manage the last card: right now 7 each => 21 per color, what about the 22th?
                }
            }
            //method that shuffles the bag
            Collections.shuffle(bag);
        }
    }
    //END REGION

    //REGION METHODS
    public boolean isBagEmpty(){
        if(bag.size() == 0) return true;
        else return false;
    }

    public Card drawCard() throws NoMoreCardsException{
            return bag.remove(bag.size() - 1);
    }
    //END REGION



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
}
