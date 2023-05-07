package it.polimi.ingsw.view;

/**
 * Defines all the methods used by the user interface (CLI/GUI) to take input from keyboard and call the methods in
 * ClientController that generate the respective message to send to the Server
 */
public interface UserInterface {

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
