/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Data: 12/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities;

import it.polimi.ingsw.server.model.entities.goals.PrivateGoal;

public class Player {

    //REGION ATTRIBUTES
    private final String username;
    private final int gameID; //per contesto multipartita
    private int score;
    private boolean isMyTurn;
    private PrivateGoal privateGoal;
    private final Bookshelf bookshelf;
    //END REGION

    /*
    if needed, the empty constructor method:
    public Player(){}
     */

    public Player(String username, int gameID){
        this.username=username;
        this.gameID=gameID;
        score = 0;
        bookshelf = new Bookshelf();
    }

    @Override
    public String toString(){
        return "\n\tUsername: " + username + "\n\tGameId: " + gameID + "\n\tScore: " + score;
    }

    //REGION METHODS
    public void addScore(int points){score+=points;}
    //END REGION

    //REGION GETTER AND SETTER
    public Bookshelf getBookshelf(){
        return bookshelf;
    }
    public void setTurn(boolean turn){
        isMyTurn = turn;
    }
    public boolean getTurn(){
        return isMyTurn;
    }
    public void setPrivateGoal(PrivateGoal privateGoal) {
        this.privateGoal = privateGoal;
    }
    public PrivateGoal getPrivateGoal() {
        return privateGoal;
    }
    //END REGION

}
