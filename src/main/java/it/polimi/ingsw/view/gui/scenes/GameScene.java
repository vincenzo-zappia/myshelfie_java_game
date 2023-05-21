package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

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

    @FXML private Button confirm;

    private ArrayList<int[]> currentSelection;


    /**
     * Creates an array of coordinates from the arraylist ...
     */
    @FXML public void onConfirmClick(){
        int[][] sentSelection = new int[currentSelection.size()][2];
        for(int i = 0; i < currentSelection.size(); i++){
            sentSelection[i][0] = currentSelection.get(i)[0];
            sentSelection[i][1] = currentSelection.get(i)[1];
        }
        controller.sendSelection(sentSelection);
        currentSelection.clear();
        for(Node node : board.getChildren()) node.setOpacity(1);
    }

    //TODO: Cambia javadoc
    /**
     * Called by the method that changes the scene. Performs initialization routines
     */
    public void initGame(){
        currentSelection = new ArrayList<>();

        for(Node node : board.getChildren()) {
            //logic
            node.setOnMouseClicked(onBoardCardClick);

            //graphic
            node.setVisible(false);
        }
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
                    //String imgPath = Main.class.getResource("") + updatedBoard[row][col].getCard().getImgPath();
                    String imgPath = "file:/C:/Users/green/Documents/GitHub/proj-ingsw-rj45/target/classes/assets/Cards/games2.png";
                    System.out.println(imgPath);
                    card.setImage(new Image(imgPath)); //TODO: Aggiungere base path bellino
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
    private Node getNodeByRowColumnIndex(final int row, final int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null && rowIndex.intValue() == row && colIndex.intValue() == col) {
                return node;
            }
        }
        return null;
    }

    EventHandler<MouseEvent> onBoardCardClick = event -> {
        Node clikedNode = (Node) event.getSource();
        ImageView selectedCard = (ImageView) clikedNode;

        //logic
        int[] selection = new int[2];
        selection[0] = GridPane.getRowIndex(clikedNode);
        selection[1] = GridPane.getColumnIndex(clikedNode);
        currentSelection.add(selection);

        //graphic
        selectedCard.setOpacity(0.5);

    };
    //endregion

}
