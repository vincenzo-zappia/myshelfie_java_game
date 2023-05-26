package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;

/**
 * Controller of the scene in which the user can choose whether to create a new lobby of join an existing one
 */
public class ConnectionScene extends GenericScene {

    /**
     * Sends a request to the server to create a new lobby and joins it
     */
    @FXML private void onCreateLobbyClick(){
        gui.loadScene("lobby.fxml");
        LobbyScene lobbyScene = (LobbyScene) gui.getController();
        lobbyScene.showStart();
        controller.createLobby();
    }

    /**
     * Changes to the scene in which the user can insert the ID of the lobby he wants to join
     */
    @FXML private void onJoinLobbyClick(){
        gui.loadScene("access.fxml");
    }

}
