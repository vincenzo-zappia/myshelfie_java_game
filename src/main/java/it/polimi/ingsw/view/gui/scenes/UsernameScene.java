package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.ClientController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UsernameScene extends GenericScene{

    @FXML private TextField textField1;


    @FXML protected void onNextClick() {
        String username = textField1.getText();
        controller.checkUsername(username);
    }

    public void init(ClientController c){
        controller = c;
    }

}
