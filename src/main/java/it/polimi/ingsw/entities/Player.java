/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.goals.PrivateGoal;

/**
 * Player of the game with a username and a bookshelf
 */
public class Player implements Comparable<Player> {

    //region ATTRIBUTES
    private final String username;
    private final Bookshelf bookshelf;
    private int score;
    private PrivateGoal privateGoal;
    //endregion

    public Player(String username){
        this.username = username;
        bookshelf = new Bookshelf();
        score = 0;
    }

    //region METHODS

    //TODO: Metodo forwarding
    public void addCardToBookshelf(int column, Card card){
        bookshelf.addCard(column, card);
    }

    /**
     * add to the player's total score the new points
     * @param points added by completing common or private goals
     */
    public void addScore(int points){score+=points;}

    //TODO: Metodo forwarding
    /**
     * check if player's bookshelf is full
     * @return boolean value (true if full)
     */
    public boolean isPlayerBookshelfFull(){
        return bookshelf.isBookshelfFull();
    }

    /**
     * override of method compareTo (cass Object)
     * @param o the object to be compared.
     * @return integer used for ordering players
     */
    @Override
    public int compareTo(Player o) {
        return Integer.compare(score, o.getScore());
    }

    //endregion

    //region GETTER AND SETTER
    public String getUsername(){
        return username;
    }
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    public int getScore(){return score;}
    public void setPrivateGoal(PrivateGoal privateGoal) {
        this.privateGoal = privateGoal;
    }
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }
    //endregion

}
