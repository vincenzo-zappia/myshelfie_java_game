package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.server.model.entities.Cell;

/*
 * Five columns of increasing or decreasing height.
 * Starting from the first column on the left or on the right,
 * each next column must be made of exactly one more tile.
 * Tiles can be of any type.
 */

public class CommonGoal12 extends CommonGoal implements Goal{

    private boolean firstCheck(Cell[] row){
        for (Cell cell : row) if (cell.isCellEmpty()) return false;
        return true;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        if(!firstCheck(bookshelf.getRow(5))) return 0; //verifico che sia presente una carta in almeno tutte le 5 colonne

        for(int i=0; i<4; i++){
            int len = bookshelf.numOfCards(i);
            int lenSucc = bookshelf.numOfCards(i+1);

            if(!(len == lenSucc+1 || len == lenSucc-1)) return 0;
        }
        return getScore();
    }
}
