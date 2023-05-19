package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller of the scene where the user can choose his username and check for its availability
 */
public class UsernameScene extends GenericScene{

    @FXML private TextField textField1; //Where to type the chosen username

    /**
     * Sends a request to the server to check the availability of the chosen username
     */
    @FXML protected void onNextClick() {
        textField1.setDisable(true);
        String username = textField1.getText();
        controller.checkUsername(username);
    }

}
