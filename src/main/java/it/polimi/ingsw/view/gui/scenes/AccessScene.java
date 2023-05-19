package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccessScene extends GenericScene{

    @FXML private TextField textField1;

    @FXML private void onNextClick(){
        try {
            int lobbyId = Integer.parseInt(textField1.getText());
            controller.joinLobby(lobbyId);
        }
        catch (NumberFormatException e){
            showMessage(false, "Insert a number!"); //todo: reviisonare messaggio
        }

    }

    @FXML private void onBackClick(){
        gui.loadScene("connection.fxml");
    }

}
