package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Generic controller with the functionalities shared by all the other specific ones
 */
public abstract class GenericScene {

    protected static ClientController controller;
    protected static GUI gui;

    @FXML private Label feedback; //Text box where the server feedback message is displayed

    //region STATIC METHOD
    public static void setController(ClientController controller){
        GenericScene.controller = controller;
    }
    public static void setGui(GUI gui){
        GenericScene.gui = gui;
    }
    //endregion

    /**
     * Displays on screen a generic feedback message sent by the server
     * @param response either positive or negative
     * @param message feedback to display
     */
    public void showMessage(boolean response, String message) {
        feedback.setVisible(true);
        if(response) feedback.setTextFill(Color.GREEN);
        else feedback.setTextFill(Color.RED);
        feedback.setText(message);
    }

    /**
     * Returns the instance of the GridPane child ImageView given its coordinates
     * @param row GridPane row coordinate fo the ImageView
     * @param col GridPane column coordinate fo the ImageView
     * @param gridPane GridPane parent of the ImageView
     * @return the instance of the ImageView
     */
    protected Node getNodeByRowColumnIndex(final int row, final int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {
                return node;
            }
        }
        return null;
    }

}
