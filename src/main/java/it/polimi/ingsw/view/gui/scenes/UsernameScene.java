package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.ClientController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class UsernameScene extends GenericScene{

    @FXML private TextField textField1;



    @FXML protected void onNextClick() {
        textField1.setDisable(true);
        String username = textField1.getText();
        controller.checkUsername(username);
    }

}
