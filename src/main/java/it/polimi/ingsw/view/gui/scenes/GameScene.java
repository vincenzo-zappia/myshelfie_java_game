package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.Main;
import it.polimi.ingsw.entities.goals.CommonGoal;
import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller of the actual game scene where the player can interact with the game entities
 */
public class GameScene extends GenericScene{

    //region FXML

    @FXML private GridPane board;
    @FXML private GridPane bookshelf;

    @FXML private ImageView cg1;
    @FXML private ImageView cg1Score;
    @FXML private ImageView cg2;
    @FXML private ImageView cg2Score;
    @FXML private ImageView token;
    @FXML private ImageView pg;

    @FXML private Button confirm;
    @FXML private Button col0;
    @FXML private Button col1;
    @FXML private Button col2;
    @FXML private Button col3;
    @FXML private Button col4;

    @FXML private TextField writeChat;
    @FXML private Label chat;

    @FXML private ImageView livingroom;

    //endregion

    private boolean selectable;

    private Tooltip cg1tooltip;
    private Tooltip cg2tooltip;
    
    private ArrayList<int[]> currentSelection;

    /**
     * Routine of game scene initialization such as binding even handlers to buttons and making image views not visible
     */
    public void initGame(){

        currentSelection = new ArrayList<>();

        //Setting up selection button events and initializing board tiles
        for(Node node : board.getChildren()) {
            node.setOnMouseClicked(onBoardCardClick);
            node.setVisible(false);
        }

        //Initializing bookshelf tiles
        for(Node node : bookshelf.getChildren()) node.setVisible(false);

        //Setting up insertion button events
        col0.setOnAction(onInsertColumnClick);
        col1.setOnAction(onInsertColumnClick);
        col2.setOnAction(onInsertColumnClick);
        col3.setOnAction(onInsertColumnClick);
        col4.setOnAction(onInsertColumnClick);

        //TODO: Fallo direttamente da scenebuilder (finire fxml)
        cg1.setVisible(false);
        cg1Score.setVisible(false);
        cg2.setVisible(false);
        cg2Score.setVisible(false);

        pg.setVisible(false);

        token.setVisible(true);

        enableConfirmationButton(false);
        selectable = true;

    }

    //region CLICKS
    /**
     * Adds or removes the coordinates of the card placed in the clicked board tile in the selection arraylist and changes its aesthetic properties
     */
    EventHandler<MouseEvent> onBoardCardClick = event -> {

        if (!selectable) return;

        Node clikedNode = (Node) event.getSource();
        ImageView selectedCard = (ImageView) clikedNode;

        //Adding the clicked card to the selection
        int[] selection = new int[2];
        selection[0] = GridPane.getRowIndex(clikedNode);
        selection[1] = GridPane.getColumnIndex(clikedNode);

        if (selectedCard.getOpacity() == 1){
            currentSelection.add(selection);

            //Changing the graphic properties of the card
            selectedCard.setOpacity(0.5);
            enableConfirmationButton(true);
        }
        else {
            //Removing the already selected coordinates from the current selection
            currentSelection.removeIf(coordinates -> Arrays.equals(coordinates, selection));

            //Changing the graphic properties of the card
            selectedCard.setOpacity(1);
            if (!cardSelected()) enableConfirmationButton(false);
        }

    };

    /**
     * Creates an array of coordinates out of the selection arraylist and sends it in a selection request to the server
     */
    @FXML public void onConfirmClick(){

        //Creating the array of coordinates out of the selection arraylist
        int[][] sentSelection = new int[currentSelection.size()][2];
        for(int i = 0; i < currentSelection.size(); i++){
            sentSelection[i][0] = currentSelection.get(i)[0];
            sentSelection[i][1] = currentSelection.get(i)[1];
        }

        //Sending the selected coordinates to the server
        controller.sendSelection(sentSelection);

        //End task routines: clearing the selection arraylist and setting the opacity back to normal
        currentSelection.clear();
        enableConfirmationButton(false);

    }

    /**
     * Extracts the number of the clicked column and sends it in an insertion request to the server
     */
    EventHandler<ActionEvent> onInsertColumnClick = event -> {
        Button column = (Button) event.getSource();
        int sentColumn = 5;
        
        //Extracting the column number from its relative button
        switch (column.getId()){
            case "col0" -> sentColumn = 0;
            case "col1" -> sentColumn = 1;
            case "col2" -> sentColumn = 2;
            case "col3" -> sentColumn = 3;
            case "col4" -> sentColumn = 4;
        }
        
        //Sending the insertion request to the server
        controller.sendInsertion(sentColumn);
        
    };
    //endregion

    //Methods called by GUIManager to update the graphical entities of the game scene during the game
    //region DISPLAYS
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
                    String imgPath = Main.getResourcePath() + updatedBoard[row][col].getCard().getImgPath();
                    card.setImage(new Image(imgPath));
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
                Node node = getNodeByRowColumnIndex(row, col, bookshelf);

                if (node instanceof ImageView) {
                    ImageView card = (ImageView) node;
                    String imgPath = Main.getResourcePath() + updatedBookshelf[row][col].getCard().getImgPath();
                    card.setImage(new Image(imgPath));
                    card.setVisible(true);
                }

            }
        }
    }

    /**
     * Displaying the randomly chosen common goal cards and updating the score whenever a player achieves one
     * @param commonGoals common goals of the current game
     */
    public void displayCommonGoals(Goal[] commonGoals){
        CommonGoal commonGoal1 = (CommonGoal) commonGoals[0];
        CommonGoal commonGoal2 = (CommonGoal) commonGoals[1];

        //Extracting the image file paths for the game common goals and their current score value
        String imgPath1 = "src/main/resources/assets/CommonGoals/" + commonGoal1.getFileName();
        String imgPath2 = "src/main/resources/assets/CommonGoals/" + commonGoal2.getFileName();
        String pathScore1 = "src/main/resources/assets/Tokens/" + commonGoal1.getScoreFileName();
        String pathScore2 = "src/main/resources/assets/Tokens/" + commonGoal2.getScoreFileName();

        //Setting the images of the game common goals and their current score value
        cg1.setImage(new Image(imgPath1));
        cg1Score.setImage(new Image(pathScore1));
        cg2.setImage(new Image(imgPath2));
        cg2Score.setImage(new Image(pathScore2));

        //Making the images visible
        cg1.setVisible(true);
        cg1Score.setVisible(true);
        cg2.setVisible(true);
        cg2Score.setVisible(true);

        cg1tooltip = new Tooltip(commonGoal1.getDescription());
        cg2tooltip = new Tooltip(commonGoal2.getDescription());
        Tooltip.install(cg1, cg1tooltip);
        Tooltip.install(cg2, cg2tooltip);

    }

    /**
     * Displays the player-specific private goal card randomly chosen for each player at the start of the game
     * @param privateGoal player-specific private goal
     */
    public void displayPrivateGoal(PrivateGoal privateGoal){
        String pgPath = "src/main/resources/assets/PrivateGoals/" + privateGoal.getFileName();
        pg.setImage(new Image(pgPath));
        pg.setVisible(true);
    }

    /**
     * Sets the cards selected from the board invisible when they are inserted in the player's bookshelf
     * @param coordinates of the board tile containing the cards to be graphically removed
     */
    public void removeCards(int[][] coordinates){
        resetBoardCardOpacity();

        for (int[] coordinate : coordinates) {
            Node node = getNodeByRowColumnIndex(coordinate[0], coordinate[1], board);
            ImageView card = (ImageView) node;
            assert card != null;
            card.setVisible(false);
        }
    }

    /**
     * Removes the token for the bonus point from the board
     * @param content message containing the username of the first player who filled his bookshelf
     */
    public void removeToken(String content){
        token.setVisible(false);
        showMessage(true, content);
    }
    //endregion

    //region CHAT
    @FXML public void sendChat(){
        String message = writeChat.getText();
        controller.sendChat(message);
        writeChat.clear();
    }

    public void showChat(String message){
        String previous = chat.getText();
        if (previous.length() > 60) chat.setText(message); //TODO: Decidere dimensione massima visualizzabile dalla chat (fxml)
        else chat.setText(previous + "\n" + message);
        chat.setVisible(true);
    }
    //endregion

    //region UTIL


    public void enableConfirmationButton(boolean enabled){
        confirm.setDisable(!enabled);
    }

    public void showInsertionButtons(boolean visibility){

    }

    private boolean cardSelected(){
        for(Node node : board.getChildren()) if (node.getOpacity() != 1) return true;

        return false;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public void resetBoardCardOpacity(){
        for(Node node : board.getChildren()) node.setOpacity(1);
        currentSelection.clear();
        enableConfirmationButton(false);
    }
    //endregion

}
