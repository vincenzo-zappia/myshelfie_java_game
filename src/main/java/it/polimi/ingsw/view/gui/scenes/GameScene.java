package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Controller of the actual game scene where the player can interact with the game entities
 */
public class GameScene extends GenericScene{

    @FXML private GridPane board;
    @FXML private GridPane bookshelf;
    @FXML private ImageView cg1;
    @FXML private ImageView cg1Score;
    @FXML private ImageView cg2;
    @FXML private ImageView cg2Score;
    @FXML private ImageView token;
    @FXML private ImageView pg;




    //TODO: Cambia javadoc
    /**
     * Called by the method that changes the scene. Performs initialization routines
     */
    public void initGame(){
        for(Node node : board.getChildren()) node.setVisible(false);
        for(Node node : bookshelf.getChildren()) node.setVisible(false);

    }

    /**
     * Updating and displaying the refilled board
     * @param updatedBoard refilled board
     */
    public void displayBoard(BoardTile[][] updatedBoard){
        int numRows = board.getRowCount();
        int numCols = board.getColumnCount();

        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numCols; col++){

                //Checking if a new card was placed in the tile
                if(!updatedBoard[row][col].isTileActive() || updatedBoard[row][col].isTileEmpty()) continue;

                //Updating and displaying the newly placed card
                Node node = getNodeByRowColumnIndex(row, col, board);
                if (node instanceof ImageView) {
                    ImageView card = (ImageView) node;
                    card.setImage(new Image(updatedBoard[row][col].getCard().getImgPath())); //TODO: Aggiungere base path bellino
                    card.setVisible(true);
                }

            }
        }
    }

    /**
     * Updating and displaying the player's bookshelf with the newly added cards
     * @param updatedBookshelf updated bookshelf
     */
    public void displayBookshelf(Tile[][] updatedBookshelf){
        int numRows = bookshelf.getRowCount();
        int numCols = bookshelf.getColumnCount();

        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numCols; col++){

                //Checking if a new card was added in the bookshelf tile
                if(updatedBookshelf[row][col].isTileEmpty()) continue;

                //Updating and displaying the newly added card
                Node node = getNodeByRowColumnIndex(row, col, board);
                if (node instanceof ImageView) {
                    ImageView card = (ImageView) node;
                    card.setImage(new Image(updatedBookshelf[row][col].getCard().getImgPath())); //TODO: Aggiungere base path bellino
                    card.setVisible(true);
                }

            }
        }
    }

    public void displayCommonGoals(Goal[] commonGoals){
        //TODO: Tutt'cos
    }

    public void displayPrivateGoal(PrivateGoal privateGoal){
        //pg.setImage(); //TODO: Implementare metodo in PrivateGoal che restituisce image path
    }

    //region UTIL
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }
    //endregion

}
