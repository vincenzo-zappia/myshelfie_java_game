package it.polimi.ingsw.server.model.entities.goals;

import it.polimi.ingsw.server.model.entities.Bookshelf;
import it.polimi.ingsw.server.model.entities.Cell;

public class CommonGoal12 implements Goal{
    private static final int SCORE = 1; //TODO: inserire valore del goal

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
        return SCORE;
    }
}
