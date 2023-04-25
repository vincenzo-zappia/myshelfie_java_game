package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.exceptions.CellGetCardException;
import it.polimi.ingsw.server.ToolXML;
import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.server.model.entities.Card;
import it.polimi.ingsw.server.model.entities.SpecialCell;

import java.util.HashMap;

public class PrivateGoal implements Goal{
    private static HashMap<Integer, Integer> scores;
    private final SpecialCell[] specialCells;

    private int getScore(int check){
        return scores.get(check);
    }

    public PrivateGoal(int id){
        //riempimento hashmap con gli score e gli obiettivi acqisiti
        scores.put(0, 0);
        scores.put(1, 1);
        scores.put(2, 2);
        scores.put(3, 4);
        scores.put(4, 6);
        scores.put(5, 9);
        scores.put(6, 12);

        specialCells = ToolXML.getSpecialCells(id);
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        int checked=0;
        for(int i=0; i< specialCells.length; i++){
            try {
                Card bookshelfCard = bookshelf.getCell(specialCells[i].getRow(), specialCells[i].getColumn()).getCard(),
                        goalCard = specialCells[i].getCard();
                if (bookshelfCard.sameType(goalCard)) checked++;

            } catch (CellGetCardException e) {
                throw new RuntimeException(e);
            }
        }
        return getScore(checked);
    }
}
