package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.util.Tile;

/*
 * Five columns of increasing or decreasing height.
 * Starting from the first column on the left or on the right,
 * each next column must be made of exactly one more tile.
 * Tiles can be of any type.
 */

public class CommonGoal12 extends CommonGoal implements Goal{

    public CommonGoal12() {
        super("Five columns of increasing or decreasing height.\n" +
                "Starting from the first column on the left or on the right,\n" +
                "each next column must be made of exactly one more tile.\n" +
                "Tiles can be of any type.", "cg12.jpg");
    }

    private boolean firstCheck(Tile[] row){
        for (Tile tile : row) if (tile.isTileEmpty()) return false;
        return true;
    }

    @Override
    public int checkGoal(Bookshelf bookshelf) {

        //Verifico che il goal non sia gia stato preso //todo: tradurre
        if(isReached()) return 0;

        if(!firstCheck(bookshelf.getRow(5))) return 0; //verifico che sia presente una carta in almeno tutte le 5 colonne

        for(int i=0; i<4; i++){
            int len = bookshelf.cardsInColumn(i);
            int lenSucc = bookshelf.cardsInColumn(i+1);

            if(!(len == lenSucc+1 || len == lenSucc-1)) return 0;
        }
        goalReached();
        return getScore();
    }
}
