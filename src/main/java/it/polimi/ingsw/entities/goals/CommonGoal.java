package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.util.CardType;
import it.polimi.ingsw.entities.util.Tile;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Generic common goal functionalities
 */
public abstract class CommonGoal implements Serializable {

    //region ATTRIBUTES
    private int score;
    private final String description;
    private final String fileName;
    private String scoreFileName;
    //endregion

    //region CONSTRUCTOR
    protected CommonGoal(String description, String fileName){
        this.fileName = "/assets/CommonGoals/" + fileName;
        this.scoreFileName = "scoring-8.jpg";
        this.description = description;
        score = 8;
    }
    //endregion

    //region METHODS
    /**
     * @param list of the tiles to check
     * @return true if all the tiles contain cards of different type
     */
    protected static boolean allDifferentType(Tile[] list) {
        HashSet<CardType> types = new HashSet<>();

        for (Tile c: list) {
            if (types.contains(c.getCard().getType())) return false;
            else types.add(c.getCard().getType());
        }
        return true;
    }

    /**
     * @param list of the tiles to check
     * @return if all the tiles contain cards of the same type
     */
    protected static boolean allSameType(Tile[] list) {
        for(int i=0; i< list.length-1; i++){
            if (list[i].getCard().getType() != list[i+1].getCard().getType()) return false;
        }
        return true;
    }

    /**
     * @param list of tiles to check
     * @return true if at least one tile is empty
     */
    protected static boolean existsEmpty(Tile[] list) {
        for (Tile c: list) if (c.isTileEmpty()) return true;
        return false;
    }

    private void decrementScore(){
        if(score>0){
            score -= 2;
            scoreFileName = "scoring-" + score + ".jpg";
        }
    }
    //endregion

    //region GETTER
    protected int getScore(){
        int oldScore = score;
        decrementScore();
        return oldScore;
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
    //endregion

}
