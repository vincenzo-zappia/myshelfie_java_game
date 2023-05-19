package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccessScene extends GenericScene{

    @FXML private TextField textField1;

    @FXML private void onStartClick(){
        controller.startGame();
    }

}
