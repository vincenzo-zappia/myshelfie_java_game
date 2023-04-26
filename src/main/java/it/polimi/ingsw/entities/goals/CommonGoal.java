package it.polimi.ingsw.entities.goals;

public abstract class CommonGoal {
    private int score = 8;

    //TODO: soluzione da migliorare, magari implementando il numero di giocatori

    private void decrementScore(){
        if(score==0) return; //TODO: generare eccezione?
        else score -= 2;
    }

    public int getScore(){
        int oldScore = score;
        decrementScore();
        return oldScore;
    }
}
