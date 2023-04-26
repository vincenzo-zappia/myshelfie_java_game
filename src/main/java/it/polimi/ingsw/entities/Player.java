/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.entities;

import it.polimi.ingsw.entities.goals.PrivateGoal;

public class Player implements Comparable<Player>{

    //region ATTRIBUTES
    private final String username;
    private final int gameID; //per contesto multipartita
    private int score;
    private boolean isMyTurn;
    private boolean firstPlayer;
    private PrivateGoal privateGoal;
    private final Bookshelf bookshelf;
    //endregion

    /*
    if needed, the empty constructor method:
    public Player(){}
     */

    //region CONSTRUCTOR
    public Player(String username, int gameID){
        this.username=username;
        this.gameID=gameID;
        score = 0;
        bookshelf = new Bookshelf();
    }
    //endregion



    //region METHODS
    public void addScore(int points){score+=points;}

    public boolean isBookshelfFull(){
        return bookshelf.checkIfFull();    //per non cambiare precedenti metodi, implementazione corretta e spostata in bookshelf.
    }

    //method to order players by score (see class Game.java)
    @Override
    public int compareTo(Player o) {
        return Integer.compare(score, o.getScore());
    }

    //endregion

    //region GETTER AND SETTER
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    public boolean getTurn(){
        return isMyTurn;
    }
    public void setTurn(boolean turn){
        isMyTurn = turn;
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
    public void setScore(int newScore){score = newScore;}  //TODO remove after test

    @Override
    public String toString(){
        return "\n\tUsername: " + username + "\n\tGameId: " + gameID + "\n\tScore: " + score;
    }

    //endregion

}
