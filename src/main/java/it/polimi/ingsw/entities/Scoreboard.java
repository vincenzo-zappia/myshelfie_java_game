package it.polimi.ingsw.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

/**
 * Class Scoreboard used in end game phase to print "username: score" ordered by descendant
 */
public class Scoreboard implements Serializable {

    /**
     * Inner class used as "struct" to associate username with scores
     */
    public class PL_score implements Serializable{
        int scores;
        String username;
    }

    private PL_score[] user_scores;

    public Scoreboard(int n_players){
        user_scores=new PL_score[n_players];
        for (int i = 0; i < n_players; i++) user_scores[i] = new PL_score();
    }

    /**
     * fill the new empty scoreboard with usernames and scores
     * @param x players arrayList (param by Game class)
     */
    public void fillScoreboard(ArrayList<Player> x){
        for(int i = 0; i<x.size(); i++){
            user_scores[i].username = x.get(i).getUsername();
            user_scores[i].scores = x.get(i).getScore();
        }
    }

    /**
     * Order by descendant score the players's username;
     */
    public void orderByDesc(){
        PL_score tmp;
        for(int k = 0; k < user_scores.length; k++){
            for(int c = 0; c< user_scores.length-1; c++){
                if(user_scores[c].scores < user_scores[c+1].scores){
                    tmp = user_scores[c+1];
                    user_scores[c+1]=user_scores[c];
                    user_scores[c] = tmp;
                }
            }
        }
    }

    public PL_score[] getUser_scores() {
        return user_scores;
    }

    public String getUsername(int y){
        return user_scores[y].username;
    }

    public int getScores(int k){
        return user_scores[k].scores;
    }
}
