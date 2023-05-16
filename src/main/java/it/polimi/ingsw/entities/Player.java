/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.goals.PrivateGoal;


public class Player implements Comparable<Player> {

    //region ATTRIBUTES
    private final String username;
    private int score;
    private PrivateGoal privateGoal;
    private final Bookshelf bookshelf;
    //endregion

    //region CONSTRUCTOR
    public Player(String username){
        this.username=username;
        score = 0;
        bookshelf = new Bookshelf();
    }

    //endregion

    //region METHODS

    public void addCardToBookshelf(int column, Card card){
        bookshelf.addCard(column, card);
    }

    /**
     * add to the player's total score the new points
     * @param points added by completing common or private goals
     */
    public void addScore(int points){score+=points;}

    /**
     * check if player's bookshelf is full
     * @return boolean value (true if full)
     */
    public boolean isBookshelfFull(){
        return bookshelf.checkIfFull();
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
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }
    public void setPrivateGoal(PrivateGoal privateGoal) {
        this.privateGoal = privateGoal;
    }
    public String getUsername(){
        return username;
    }
    public int getScore(){return score;}

    //endregion

}
