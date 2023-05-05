package it.polimi.ingsw.view;

public interface UserAction {

    /**
     * The client interface asks the player to select the cards
     */
    void requestCardSelection();

    /**
     * The client interface asks the player to choose the order of the cards to insert into his
     * bookshelf and the column where to insert them
     */
    void requestCardInsertion();

}
