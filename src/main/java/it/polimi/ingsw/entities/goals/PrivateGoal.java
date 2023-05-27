package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.entities.util.Tile;
import it.polimi.ingsw.entities.util.SpatialTile;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Player-specific private goal
 */
public class PrivateGoal implements Goal, Serializable {

    //region ATTRIBUTES
    private static HashMap<Integer, Integer> scores;
    private final SpatialTile[] spatialTiles;
    private final String fileName;
    //endregion

    //region CONSTRUCTOR
    public PrivateGoal(SpatialTile[] spatialTiles, String fileName){

        //Creating a hashmap with the correspondence between number of achieved goals and scored points
        scores = new HashMap<>();
        scores.put(0, 0);
        scores.put(1, 1);
        scores.put(2, 2);
        scores.put(3, 4);
        scores.put(4, 6);
        scores.put(5, 9);
        scores.put(6, 12);

        this.fileName = fileName;
        this.spatialTiles = spatialTiles;
    }
    //endregion

    //region METHODS
    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int checked=0;
        for (SpatialTile spatialTile : spatialTiles) {
            if(bookshelf.getBookshelfTile(spatialTile.getRow(),spatialTile.getColumn()).getCard() != null){
                Card bookshelfCard = bookshelf.getBookshelfTile(spatialTile.getRow(), spatialTile.getColumn()).getCard(), goalCard = spatialTile.getCard();
                if (bookshelfCard.sameType(goalCard)) checked++;
            }
        }
        return getScore(checked);
    }
    //endregion

    //region GETTER
    private int getScore(int check){
        return scores.get(check);
    }

    public String getFileName() {
        return "/assets/PrivateGoals/" + fileName;
    }

    public Tile[][] getGoalStructure(){

        Tile[][] structure = new Tile[6][5];
        for(int i=0; i<6;i++) for(int j=0; j<5; j++) structure[i][j] = new Tile();

        for(SpatialTile spatialTile : spatialTiles) structure[spatialTile.getRow()][spatialTile.getColumn()].setCard(spatialTile.getCard());

        return structure;
    }
    //endregion

}
