package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;

public class ConnectionScene extends GenericScene {
    @FXML private void newMatchClick(){
        controller.createLobby();
        gui.loadScene("lobby.fxml");
    }

    @FXML private void existingMatchClick(){
        gui.loadScene("access.fxml");
    }
}
