package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.util.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card card;

    @BeforeEach
    void setup(){
        card = new Card("frames1.png", CardType.FRAMES);
    }

    @Test
    void getImgPath(){
        assertEquals("/assets/Cards/frames1.png", card.getImgPath());
    }

    @Test
    void getType(){
        assertEquals(CardType.FRAMES, card.getType());
    }

    @Test
    void sameType(){
        Card card1 = new Card("frames2.png", CardType.FRAMES);
        assertTrue(card.sameType(card1));
        assertTrue(card1.sameType(card));
    }

    @Test
    void differentType(){
        Card card1 = new Card("cats1.png", CardType.CATS);
        assertFalse(card.sameType(card1));
        assertFalse(card1.sameType(card));
    }

}