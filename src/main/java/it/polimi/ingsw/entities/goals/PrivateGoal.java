package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.util.ToolXML;
import it.polimi.ingsw.util.SpatialTile;

import java.io.Serializable;
import java.util.HashMap;

public class PrivateGoal implements Goal, Serializable {
    private static HashMap<Integer, Integer> scores;
    private final SpatialTile[] spatialTiles;

    private int getScore(int check){
        return scores.get(check);
    }

    public PrivateGoal(int id){
        //riempimento hashmap con gli score e gli obiettivi acqisiti
        scores = new HashMap<>(){{
            put(0, 0);
            put(1, 1);
            put(2, 2);
            put(3, 4);
            put(4, 6);
            put(5, 9);
            put(6, 12);
        }};


        spatialTiles = ToolXML.getSpecialCells(id);
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int checked=0;
        for (SpatialTile spatialTile : spatialTiles) {
            try {
                Card bookshelfCard = bookshelf.getBookshelfTile(spatialTile.getRow(), spatialTile.getColumn()).getCard(),
                        goalCard = spatialTile.getCard();
                if (bookshelfCard.sameType(goalCard)) checked++;

            } catch (CellGetCardException e) {
                throw new RuntimeException(e);
            }
        }
        return getScore(checked);
    }
}
