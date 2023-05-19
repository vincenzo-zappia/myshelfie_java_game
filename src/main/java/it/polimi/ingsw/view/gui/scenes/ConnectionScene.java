package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;

public class ConnectionScene extends GenericScene {
    @FXML private void newMatchClick(){
        gui.loadScene("lobby.fxml");
        controller.createLobby();
    }

    @FXML private void existingMatchClick(){
        gui.loadScene("access.fxml");
    }
}
