package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;

import java.io.Serializable;
import java.util.HashSet;

public abstract class CommonGoal implements Serializable {

    //region ATTRIBUTES
    private int score;
    private boolean reached;
    private final String description;
    private final String fileName;
    private String scoreFileName;
    //endregion

    protected CommonGoal(String description, String fileName){
        this.fileName = "/assets/CommonGoals/" + fileName;
        this.scoreFileName = "scoring-8.jpg";
        this.description = description;
        this.reached = false;
        score = 8;
    }

    protected static boolean allTypesDifferent(Tile[] list) {
        HashSet<CardType> types = new HashSet<>();

        for (Tile c: list) {
            if (types.contains(c.getCard().getType())) return false;
            else types.add(c.getCard().getType());
        }
        return true;
    }

    protected static boolean sameTypes(Tile[] list) {
        for(int i=0; i< list.length-1; i++){
            if (list[i].getCard().getType() != list[i+1].getCard().getType()) return false;
        }
        return true;
    }

    protected static boolean existEmpty(Tile[] list) {
        for (Tile c: list) if (c.isTileEmpty()) return true;
        return false;
    }

    private void decrementScore(){
        if(score>0){
            score -= 2;
            scoreFileName = "scoring-" + score + ".jpg";
        }
    }

    protected int getScore(){
        int oldScore = score;
        decrementScore();
        return oldScore;
    }

    protected void goalReached(){
        reached = true;
    }

    protected boolean isReached(){
        return reached;
    }

    public String getDescription() {
        return description;
    }

    public String getFileName(){
        return fileName;
    }

    public String getScoreFileName(){
        return "\\assets\\Tokens\\" + scoreFileName;
    }
}
