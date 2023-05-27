/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.entities;
import it.polimi.ingsw.entities.goals.PrivateGoal;

import java.io.Serializable;

/**
 * Player of the game with a username and a bookshelf
 */
public class Player implements Comparable<Player>, Serializable {

    //region ATTRIBUTES
    private final String username;
    private final Bookshelf bookshelf;
    private int score;
    private final PrivateGoal privateGoal;
    private final boolean[] scoredCG;

    //endregion

    //region CONSTRUCTOR
    public Player(String username, PrivateGoal privateGoal){
        this.privateGoal = privateGoal;
        this.username = username;
        bookshelf = new Bookshelf();
        score = 0;
        scoredCG = new boolean[2];
    }
    //endregion

    //region METHODS
    /**
     * Adds points to the player's score
     * @param points added
     */
    public void addScore(int points){
        score+=points;
    }

    public void scoreCommonGoal(int i){
        scoredCG[i] = true;
    }

    public boolean isCommonGoalScored(int i){
        return scoredCG[i];
    }

    /**
     * Compares the current player to another by score
     * @param other to be compared
     * @return integer codifying the comparison
     */
    @Override
    public int compareTo(Player other) {
        return Integer.compare(other.score, this.score);
    }
    //endregion

    //region GETTER
    public String getUsername(){
        return username;
    }
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    public int getScore(){return score;}
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }
    //endregion

}
