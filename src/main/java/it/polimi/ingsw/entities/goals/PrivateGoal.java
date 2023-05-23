package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.GetCardException;
import it.polimi.ingsw.mechanics.ToolXML;
import it.polimi.ingsw.entities.util.SpatialTile;

import java.io.Serializable;
import java.util.HashMap;

public class PrivateGoal implements Goal, Serializable {
    private static HashMap<Integer, Integer> scores;
    private final SpatialTile[] spatialTiles;
    private final String fileName;

    private int getScore(int check){
        return scores.get(check);
    }

    public PrivateGoal(SpatialTile[] spatialTiles, String fileName){
        //riempimento hashmap con gli score e gli obiettivi acqisiti
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

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int checked=0;
        for (SpatialTile spatialTile : spatialTiles) {
            try {
                Card bookshelfCard = bookshelf.getBookshelfTile(spatialTile.getRow(), spatialTile.getColumn()).getCard(),
                        goalCard = spatialTile.getCard();
                if (bookshelfCard.sameType(goalCard)) checked++;

            } catch (GetCardException e) {
                throw new RuntimeException(e);
            }
        }
        return getScore(checked);
    }

    public String getFileName() {
        return "\\assets\\PrivateGoals\\" + fileName;
    }
}
