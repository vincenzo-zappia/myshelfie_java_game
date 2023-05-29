package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller of the scene in which the user can insert the ID of the lobby that he wants to join
 */
public class AccessScene extends GenericScene{

    @FXML private TextField textField1; //Where to type the lobby ID

    /**
     * Sends a request to the server to join the lobby corresponding to the ID selected by the user
     */
    @FXML private void onNextClick(){
        try {
            int lobbyId = Integer.parseInt(textField1.getText());
            controller.joinLobby(lobbyId);
        }
        catch (NumberFormatException e){
            showMessage(false, "Invalid input, insert a number!");
        }
    }

    /**
     * Goes back to the scene where the player can choose whether to create or join a lobby
     */
    @FXML private void onBackClick(){
        gui.loadScene("connection.fxml");
    }

}
