package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.util.CardType;
import it.polimi.ingsw.util.Cell;

import java.util.HashSet;

public abstract class CommonGoal {

    //region ATTRIBUTES
    private int score;
    private final String description;
    //endregion

    protected CommonGoal(String description){
        score = 8;
        this.description = description;
    }

    protected static boolean allTypesDifferent(Cell[] list) {
        HashSet<CardType> types = new HashSet<>();

        for (Cell c: list) {
            if (types.contains(c.getCard().getType())) return false;
            else types.add(c.getCard().getType());
        }
        return true;
    }

    protected static boolean sameTypes(Cell[] list) {
        for(int i=0; i< list.length-1; i++){
            if (!(list[i]==list[i+1])) return false;
        }
        return true;
    }

    protected static boolean existEmpty(Cell[] list) {
        for (Cell c: list) if (c.isCellEmpty()) return true;
        return false;
    }

    //TODO: soluzione da migliorare, magari implementando il numero di giocatori

    private void decrementScore(){
        if(score==0) return; //TODO: generare eccezione?
        else score -= 2;
    }

    protected int getScore(){
        int oldScore = score;
        decrementScore();
        return oldScore;
    }

    public String getDescription() {
        return description;
    }
}
