package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.ClientController;
import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Generic controller with the functionalities shared by all the other specific ones
 */
public abstract class GenericScene {

    protected static ClientController controller;
    protected static GUI gui;

    @FXML private Label feedback; //Text box where the server feedback message is displayed

    public static void setController(ClientController controller){
        GenericScene.controller = controller;
    }
    public static void setGui(GUI gui){ GenericScene.gui = gui; }

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

}
